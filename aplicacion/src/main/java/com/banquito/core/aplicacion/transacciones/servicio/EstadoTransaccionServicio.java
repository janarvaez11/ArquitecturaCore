package com.banquito.core.aplicacion.transacciones.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public EstadoTransaccionServicio(EstadoTransaccionRepositorio estadoTransaccionRepositorio) {
        this.estadoTransaccionRepositorio = estadoTransaccionRepositorio;
    }

    public EstadoTransaccion findById(String estadoId) {
        if (estadoId == null || estadoId.trim().isEmpty()) {
            throw new EstadoTransaccionNoEncontradaExcepcion("El ID del estado de transacción no puede ser nulo o vacío");
        }
        
        Optional<EstadoTransaccion> estadoOptional = this.estadoTransaccionRepositorio.findById(estadoId);
        if (estadoOptional.isPresent()) {
            return estadoOptional.get();
        } else {
            throw new EstadoTransaccionNoEncontradaExcepcion("El estado de transacción con ID: " + estadoId + " no existe");
        }
    }

    public List<EstadoTransaccion> findAll() {
        List<EstadoTransaccion> estados = this.estadoTransaccionRepositorio.findAll();
        if (estados.isEmpty()) {
            throw new EstadoTransaccionNoEncontradaExcepcion("No existen estados de transacción registrados");
        }
        return estados;
    }

    @Transactional
    public EstadoTransaccion create(EstadoTransaccion estadoTransaccion) {
        if (estadoTransaccion == null) {
            throw new EstadoTransaccionNoEncontradaExcepcion("El estado de transacción no puede ser nulo");
        }

        // Validaciones de reglas de negocio
        if (estadoTransaccion.getEstadoId() == null || estadoTransaccion.getEstadoId().trim().isEmpty()) {
            throw new EstadoTransaccionNoEncontradaExcepcion("El ID del estado es obligatorio");
        }

        if (estadoTransaccion.getEstadoId().length() > 3) {
            throw new EstadoTransaccionNoEncontradaExcepcion("El ID del estado no puede tener más de 3 caracteres");
        }

        // Verificar que no exista un estado con el mismo ID
        if (this.estadoTransaccionRepositorio.existsById(estadoTransaccion.getEstadoId())) {
            throw new EstadoTransaccionNoEncontradaExcepcion("Ya existe un estado con el ID: " + estadoTransaccion.getEstadoId());
        }

        if (estadoTransaccion.getNombre() == null || estadoTransaccion.getNombre().trim().isEmpty()) {
            throw new EstadoTransaccionNoEncontradaExcepcion("El nombre del estado es obligatorio");
        }

        if (estadoTransaccion.getDescripcion() == null || estadoTransaccion.getDescripcion().trim().isEmpty()) {
            throw new EstadoTransaccionNoEncontradaExcepcion("La descripción del estado es obligatoria");
        }

        // Establecer fecha de creación
        estadoTransaccion.setCreadoEn(new java.sql.Timestamp(System.currentTimeMillis()));
        
        return this.estadoTransaccionRepositorio.save(estadoTransaccion);
    }

    @Transactional
    public EstadoTransaccion update(String estadoId, EstadoTransaccion estadoTransaccion) {
        EstadoTransaccion estadoExistente = this.findById(estadoId);
        
        if (estadoTransaccion.getNombre() != null && !estadoTransaccion.getNombre().trim().isEmpty()) {
            estadoExistente.setNombre(estadoTransaccion.getNombre());
        }
        
        if (estadoTransaccion.getDescripcion() != null && !estadoTransaccion.getDescripcion().trim().isEmpty()) {
            estadoExistente.setDescripcion(estadoTransaccion.getDescripcion());
        }

        return this.estadoTransaccionRepositorio.save(estadoExistente);
    }

    public boolean esEstadoValido(String estadoId) {
        return estadoId != null && 
               (ESTADO_PENDIENTE.equals(estadoId) || 
                ESTADO_COMPLETADO.equals(estadoId) || 
                ESTADO_CANCELADO.equals(estadoId) || 
                ESTADO_FALLIDO.equals(estadoId));
    }

    public boolean puedeTransicionarA(String estadoActual, String nuevoEstado) {
        if (ESTADO_PENDIENTE.equals(estadoActual)) {
            return ESTADO_COMPLETADO.equals(nuevoEstado) || 
                   ESTADO_CANCELADO.equals(nuevoEstado) || 
                   ESTADO_FALLIDO.equals(nuevoEstado);
        }
        
        // Los estados finales no pueden cambiar
        return false;
    }

    @Transactional
    public void delete(String estadoId) {
        EstadoTransaccion estadoTransaccion = this.findById(estadoId);
        this.estadoTransaccionRepositorio.delete(estadoTransaccion);
    }
} 