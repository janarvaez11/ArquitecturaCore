package com.banquito.core.aplicacion.transacciones.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.transacciones.excepcion.EstadoTransaccionInvalidaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.EstadoTransaccionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.modelo.EstadoTransaccion;
import com.banquito.core.aplicacion.transacciones.repositorio.EstadoTransaccionRepositorio;

@Service
public class EstadoTransaccionServicio {

    private final EstadoTransaccionRepositorio estadoTransaccionRepositorio;

    // Estados predefinidos para validaciones
    private static final String ESTADO_PENDIENTE = "PEN";
    private static final String ESTADO_COMPLETADO = "COM";
    private static final String ESTADO_CANCELADO = "CAN";
    private static final String ESTADO_FALLIDO = "FAL";
    private static final String ESTADO_PROCESANDO = "PRO";
    private static final String ESTADO_REVERTIDO = "REV";

    // Estados que no se pueden eliminar (críticos para el sistema)
    private static final List<String> ESTADOS_SISTEMA = List.of(
        ESTADO_PENDIENTE, ESTADO_COMPLETADO, ESTADO_CANCELADO, ESTADO_FALLIDO);

    public EstadoTransaccionServicio(EstadoTransaccionRepositorio estadoTransaccionRepositorio) {
        this.estadoTransaccionRepositorio = estadoTransaccionRepositorio;
    }

    @Transactional(readOnly = true)
    public EstadoTransaccion findById(String estadoId) {
        this.validarIdEstado(estadoId);
        
        Optional<EstadoTransaccion> estadoOptional = this.estadoTransaccionRepositorio.findById(estadoId);
        if (estadoOptional.isPresent()) {
            return estadoOptional.get();
        } else {
            throw new EstadoTransaccionNoEncontradaExcepcion("El estado de transacción con ID: " + estadoId + " no existe");
        }
    }

    @Transactional(readOnly = true)
    public List<EstadoTransaccion> findAll() {
        List<EstadoTransaccion> estados = this.estadoTransaccionRepositorio.findAll();
        if (estados.isEmpty()) {
            throw new EstadoTransaccionNoEncontradaExcepcion("No existen estados de transacción registrados");
        }
        return estados;
    }

    @Transactional(readOnly = true)
    public List<EstadoTransaccion> findEstadosDisponibles() {
        List<EstadoTransaccion> estadosDisponibles = this.estadoTransaccionRepositorio
            .findByEstadoIdIn(List.of(ESTADO_PENDIENTE, ESTADO_COMPLETADO, ESTADO_CANCELADO));
        
        if (estadosDisponibles.isEmpty()) {
            throw new EstadoTransaccionNoEncontradaExcepcion("No se encontraron estados disponibles para transacciones");
        }
        return estadosDisponibles;
    }

    @Transactional(readOnly = true)
    public List<EstadoTransaccion> findEstadosFinales() {
        List<EstadoTransaccion> estadosFinales = this.estadoTransaccionRepositorio
            .findByEstadoIdIn(List.of(ESTADO_COMPLETADO, ESTADO_CANCELADO, ESTADO_FALLIDO, ESTADO_REVERTIDO));
        
        return estadosFinales;
    }

    @Transactional
    public EstadoTransaccion create(EstadoTransaccion estadoTransaccion) {
        this.validarEstadoParaCreacion(estadoTransaccion);
        
        this.validarIdUnico(estadoTransaccion.getEstadoId());
        
        this.validarNombreUnico(estadoTransaccion.getNombre(), null);

        estadoTransaccion.setCreadoEn(new java.sql.Timestamp(System.currentTimeMillis()));
        
        try {
            return this.estadoTransaccionRepositorio.save(estadoTransaccion);
        } catch (Exception e) {
            throw new EstadoTransaccionInvalidaExcepcion("Error al crear el estado de transacción: " + e.getMessage());
        }
    }

    @Transactional
    public EstadoTransaccion update(String estadoId, EstadoTransaccion estadoTransaccion) {
        this.validarIdEstado(estadoId);
        EstadoTransaccion estadoExistente = this.findById(estadoId);
        
        if (ESTADOS_SISTEMA.contains(estadoId)) {
            throw new EstadoTransaccionInvalidaExcepcion("No se puede modificar un estado crítico del sistema: " + estadoId);
        }
        
        if (estadoTransaccion == null) {
            throw new EstadoTransaccionInvalidaExcepcion("Los datos del estado no pueden ser nulos");
        }
        
        if (estadoTransaccion.getNombre() != null && !estadoTransaccion.getNombre().trim().isEmpty()) {
            String nuevoNombre = estadoTransaccion.getNombre().trim();
            this.validarLongitudNombre(nuevoNombre);
            this.validarNombreUnico(nuevoNombre, estadoId);
            estadoExistente.setNombre(nuevoNombre);
        }
        
        if (estadoTransaccion.getDescripcion() != null && !estadoTransaccion.getDescripcion().trim().isEmpty()) {
            String nuevaDescripcion = estadoTransaccion.getDescripcion().trim();
            this.validarLongitudDescripcion(nuevaDescripcion);
            estadoExistente.setDescripcion(nuevaDescripcion);
        }

        try {
            return this.estadoTransaccionRepositorio.save(estadoExistente);
        } catch (Exception e) {
            throw new EstadoTransaccionInvalidaExcepcion("Error al actualizar el estado de transacción: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public boolean esEstadoValido(String estadoId) {
        if (estadoId == null || estadoId.trim().isEmpty()) {
            return false;
        }
        
        return this.estadoTransaccionRepositorio.existsById(estadoId.trim().toUpperCase());
    }

    @Transactional(readOnly = true)
    public boolean esEstadoFinal(String estadoId) {
        return ESTADO_COMPLETADO.equals(estadoId) || 
               ESTADO_CANCELADO.equals(estadoId) || 
               ESTADO_FALLIDO.equals(estadoId) ||
               ESTADO_REVERTIDO.equals(estadoId);
    }

    @Transactional(readOnly = true)
    public boolean puedeTransicionarA(String estadoActual, String nuevoEstado) {
        if (estadoActual == null || nuevoEstado == null) {
            return false;
        }
        
        if (!this.esEstadoValido(estadoActual) || !this.esEstadoValido(nuevoEstado)) {
            return false;
        }
        
        switch (estadoActual) {
            case ESTADO_PENDIENTE:
                return ESTADO_PROCESANDO.equals(nuevoEstado) || 
                       ESTADO_COMPLETADO.equals(nuevoEstado) || 
                       ESTADO_CANCELADO.equals(nuevoEstado) || 
                       ESTADO_FALLIDO.equals(nuevoEstado);
                       
            case ESTADO_PROCESANDO:
                return ESTADO_COMPLETADO.equals(nuevoEstado) || 
                       ESTADO_FALLIDO.equals(nuevoEstado);
                       
            case ESTADO_COMPLETADO:
                return ESTADO_REVERTIDO.equals(nuevoEstado);
                
            case ESTADO_CANCELADO:
            case ESTADO_FALLIDO:
            case ESTADO_REVERTIDO:
                return false;
                
            default:
                return false;
        }
    }

    @Transactional(readOnly = true)
    public String obtenerEstadoSiguienteRecomendado(String estadoActual) {
        switch (estadoActual) {
            case ESTADO_PENDIENTE:
                return ESTADO_PROCESANDO;
            case ESTADO_PROCESANDO:
                return ESTADO_COMPLETADO;
            default:
                throw new EstadoTransaccionInvalidaExcepcion("No hay estado siguiente recomendado para: " + estadoActual);
        }
    }

    @Transactional
    public void delete(String estadoId) {
        this.validarIdEstado(estadoId);
        
        if (ESTADOS_SISTEMA.contains(estadoId)) {
            throw new EstadoTransaccionInvalidaExcepcion("No se puede eliminar un estado crítico del sistema: " + estadoId);
        }
        
        EstadoTransaccion estadoTransaccion = this.findById(estadoId);
        
        this.validarPuedeBorrarse(estadoId);
        
        try {
            this.estadoTransaccionRepositorio.delete(estadoTransaccion);
        } catch (Exception e) {
            throw new EstadoTransaccionInvalidaExcepcion("Error al eliminar el estado de transacción: " + e.getMessage());
        }
    }

    // Métodos privados de validación

    private void validarIdEstado(String estadoId) {
        if (estadoId == null || estadoId.trim().isEmpty()) {
            throw new EstadoTransaccionInvalidaExcepcion("El ID del estado de transacción no puede ser nulo o vacío");
        }
        
        if (estadoId.length() > 3) {
            throw new EstadoTransaccionInvalidaExcepcion("El ID del estado no puede tener más de 3 caracteres");
        }
    }

    private void validarEstadoParaCreacion(EstadoTransaccion estadoTransaccion) {
        if (estadoTransaccion == null) {
            throw new EstadoTransaccionInvalidaExcepcion("El estado de transacción no puede ser nulo");
        }

        if (estadoTransaccion.getEstadoId() == null || estadoTransaccion.getEstadoId().trim().isEmpty()) {
            throw new EstadoTransaccionInvalidaExcepcion("El ID del estado es obligatorio");
        }
        
        String estadoId = estadoTransaccion.getEstadoId().trim().toUpperCase();
        this.validarIdEstado(estadoId);
        estadoTransaccion.setEstadoId(estadoId);

        if (estadoTransaccion.getNombre() == null || estadoTransaccion.getNombre().trim().isEmpty()) {
            throw new EstadoTransaccionInvalidaExcepcion("El nombre del estado es obligatorio");
        }
        this.validarLongitudNombre(estadoTransaccion.getNombre().trim());

        if (estadoTransaccion.getDescripcion() == null || estadoTransaccion.getDescripcion().trim().isEmpty()) {
            throw new EstadoTransaccionInvalidaExcepcion("La descripción del estado es obligatoria");
        }
        this.validarLongitudDescripcion(estadoTransaccion.getDescripcion().trim());
    }

    private void validarLongitudNombre(String nombre) {
        if (nombre.length() > 50) {
            throw new EstadoTransaccionInvalidaExcepcion("El nombre del estado no puede exceder 50 caracteres");
        }
        if (nombre.length() < 3) {
            throw new EstadoTransaccionInvalidaExcepcion("El nombre del estado debe tener al menos 3 caracteres");
        }
    }

    private void validarLongitudDescripcion(String descripcion) {
        if (descripcion.length() > 200) {
            throw new EstadoTransaccionInvalidaExcepcion("La descripción del estado no puede exceder 200 caracteres");
        }
        if (descripcion.length() < 10) {
            throw new EstadoTransaccionInvalidaExcepcion("La descripción del estado debe tener al menos 10 caracteres");
        }
    }

    private void validarIdUnico(String estadoId) {
        if (this.estadoTransaccionRepositorio.existsById(estadoId)) {
            throw new EstadoTransaccionInvalidaExcepcion("Ya existe un estado con el ID: " + estadoId);
        }
    }

    private void validarNombreUnico(String nombre, String idExcluir) {
        List<EstadoTransaccion> estadosExistentes = this.estadoTransaccionRepositorio.findByNombreIgnoreCase(nombre);
        
        if (!estadosExistentes.isEmpty()) {
            if (idExcluir != null) {
                estadosExistentes = estadosExistentes.stream()
                    .filter(estado -> !estado.getEstadoId().equals(idExcluir))
                    .toList();
            }
            
            if (!estadosExistentes.isEmpty()) {
                throw new EstadoTransaccionInvalidaExcepcion("Ya existe un estado con el nombre: " + nombre);
            }
        }
    }

    private void validarPuedeBorrarse(String estadoId) {
        boolean tieneTransaccionesAsociadas = this.estadoTransaccionRepositorio.existsTransaccionesByEstadoId(estadoId);
        if (tieneTransaccionesAsociadas) {
            throw new EstadoTransaccionInvalidaExcepcion("No se puede eliminar el estado porque tiene transacciones asociadas");
        }
    }
} 