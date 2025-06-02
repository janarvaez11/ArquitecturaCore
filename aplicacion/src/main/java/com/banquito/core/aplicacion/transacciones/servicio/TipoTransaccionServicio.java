package com.banquito.core.aplicacion.transacciones.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.transacciones.excepcion.ComisionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.TipoTransaccionDuplicadaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.TipoTransaccionInvalidaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.TipoTransaccionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.modelo.ComisionTransaccion;
import com.banquito.core.aplicacion.transacciones.modelo.TipoTransaccion;
import com.banquito.core.aplicacion.transacciones.repositorio.ComisionTransaccionRepositorio;
import com.banquito.core.aplicacion.transacciones.repositorio.TipoTransaccionRepositorio;

@Service
public class TipoTransaccionServicio {

    private final TipoTransaccionRepositorio tipoTransaccionRepositorio;
    private final ComisionTransaccionRepositorio comisionTransaccionRepositorio;

    public TipoTransaccionServicio(TipoTransaccionRepositorio tipoTransaccionRepositorio, 
                                   ComisionTransaccionRepositorio comisionTransaccionRepositorio) {
        this.tipoTransaccionRepositorio = tipoTransaccionRepositorio;
        this.comisionTransaccionRepositorio = comisionTransaccionRepositorio;
    }

    @Transactional(readOnly = true)
    public TipoTransaccion findById(Integer id) {
        this.validarIdTipoTransaccion(id);
        
        Optional<TipoTransaccion> tipoTransaccionOptional = this.tipoTransaccionRepositorio.findById(id);
        if (tipoTransaccionOptional.isPresent()) {
            return tipoTransaccionOptional.get();
        } else {
            throw new TipoTransaccionNoEncontradaExcepcion("El tipo de transacción con ID: " + id + " no existe");
        }
    }

    @Transactional(readOnly = true)
    public List<TipoTransaccion> findAll() {
        List<TipoTransaccion> tipos = this.tipoTransaccionRepositorio.findAll();
        if (tipos.isEmpty()) {
            throw new TipoTransaccionNoEncontradaExcepcion("No existen tipos de transacción registrados");
        }
        return tipos;
    }

    @Transactional(readOnly = true)
    public List<TipoTransaccion> findByNombreContaining(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new TipoTransaccionInvalidaExcepcion("El nombre para la búsqueda no puede ser nulo o vacío");
        }
        
        List<TipoTransaccion> tipos = this.tipoTransaccionRepositorio.findByNombreContainingIgnoreCase(nombre.trim());
        if (tipos.isEmpty()) {
            throw new TipoTransaccionNoEncontradaExcepcion("No se encontraron tipos de transacción con el nombre: " + nombre);
        }
        return tipos;
    }

    @Transactional
    public TipoTransaccion create(TipoTransaccion tipoTransaccion) {
        this.validarTipoTransaccionParaCreacion(tipoTransaccion);
        
        // Validar que la comisión existe
        this.validarComisionExistente(tipoTransaccion.getComisionId());
        
        // Validar que no existe un tipo de transacción con el mismo nombre
        this.validarNombreUnico(tipoTransaccion.getNombre(), null);
        
        // Establecer fecha de creación
        tipoTransaccion.setCreadoEn(new java.sql.Timestamp(System.currentTimeMillis()));
        
        try {
            return this.tipoTransaccionRepositorio.save(tipoTransaccion);
        } catch (Exception e) {
            throw new TipoTransaccionInvalidaExcepcion("Error al crear el tipo de transacción: " + e.getMessage());
        }
    }

    @Transactional
    public TipoTransaccion update(Integer id, TipoTransaccion tipoTransaccion) {
        this.validarIdTipoTransaccion(id);
        TipoTransaccion tipoExistente = this.findById(id);
        
        // Validar datos de actualización
        if (tipoTransaccion == null) {
            throw new TipoTransaccionInvalidaExcepcion("Los datos del tipo de transacción no pueden ser nulos");
        }
        
        // Actualizar nombre si se proporciona
        if (tipoTransaccion.getNombre() != null && !tipoTransaccion.getNombre().trim().isEmpty()) {
            String nuevoNombre = tipoTransaccion.getNombre().trim();
            this.validarLongitudNombre(nuevoNombre);
            this.validarNombreUnico(nuevoNombre, id);
            tipoExistente.setNombre(nuevoNombre);
        }
        
        // Actualizar descripción si se proporciona
        if (tipoTransaccion.getDescripcion() != null && !tipoTransaccion.getDescripcion().trim().isEmpty()) {
            String nuevaDescripcion = tipoTransaccion.getDescripcion().trim();
            this.validarLongitudDescripcion(nuevaDescripcion);
            tipoExistente.setDescripcion(nuevaDescripcion);
        }
        
        // Actualizar comisión si se proporciona
        if (tipoTransaccion.getComisionId() != null && tipoTransaccion.getComisionId() > 0) {
            this.validarComisionExistente(tipoTransaccion.getComisionId());
            tipoExistente.setComisionId(tipoTransaccion.getComisionId());
        }

        try {
            return this.tipoTransaccionRepositorio.save(tipoExistente);
        } catch (Exception e) {
            throw new TipoTransaccionInvalidaExcepcion("Error al actualizar el tipo de transacción: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        this.validarIdTipoTransaccion(id);
        TipoTransaccion tipoTransaccion = this.findById(id);
        
        // Validar que no hay transacciones asociadas (regla de negocio)
        this.validarPuedeBorrarse(id);
        
        try {
            this.tipoTransaccionRepositorio.delete(tipoTransaccion);
        } catch (Exception e) {
            throw new TipoTransaccionInvalidaExcepcion("Error al eliminar el tipo de transacción: " + e.getMessage());
        }
    }

    // Métodos privados de validación de reglas de negocio

    private void validarIdTipoTransaccion(Integer id) {
        if (id == null || id <= 0) {
            throw new TipoTransaccionInvalidaExcepcion("El ID del tipo de transacción debe ser un número entero positivo mayor a 0");
        }
    }

    private void validarTipoTransaccionParaCreacion(TipoTransaccion tipoTransaccion) {
        if (tipoTransaccion == null) {
            throw new TipoTransaccionInvalidaExcepcion("El tipo de transacción no puede ser nulo");
        }

        // Validar nombre
        if (tipoTransaccion.getNombre() == null || tipoTransaccion.getNombre().trim().isEmpty()) {
            throw new TipoTransaccionInvalidaExcepcion("El nombre del tipo de transacción es obligatorio");
        }
        this.validarLongitudNombre(tipoTransaccion.getNombre().trim());

        // Validar descripción
        if (tipoTransaccion.getDescripcion() == null || tipoTransaccion.getDescripcion().trim().isEmpty()) {
            throw new TipoTransaccionInvalidaExcepcion("La descripción del tipo de transacción es obligatoria");
        }
        this.validarLongitudDescripcion(tipoTransaccion.getDescripcion().trim());

        // Validar comisión ID
        if (tipoTransaccion.getComisionId() == null || tipoTransaccion.getComisionId() <= 0) {
            throw new TipoTransaccionInvalidaExcepcion("El ID de comisión debe ser un número entero positivo mayor a 0");
        }
    }

    private void validarLongitudNombre(String nombre) {
        if (nombre.length() > 50) {
            throw new TipoTransaccionInvalidaExcepcion("El nombre del tipo de transacción no puede exceder 50 caracteres");
        }
        if (nombre.length() < 3) {
            throw new TipoTransaccionInvalidaExcepcion("El nombre del tipo de transacción debe tener al menos 3 caracteres");
        }
    }

    private void validarLongitudDescripcion(String descripcion) {
        if (descripcion.length() > 100) {
            throw new TipoTransaccionInvalidaExcepcion("La descripción del tipo de transacción no puede exceder 100 caracteres");
        }
        if (descripcion.length() < 10) {
            throw new TipoTransaccionInvalidaExcepcion("La descripción del tipo de transacción debe tener al menos 10 caracteres");
        }
    }

    private void validarComisionExistente(Integer comisionId) {
        Optional<ComisionTransaccion> comisionOptional = this.comisionTransaccionRepositorio.findById(comisionId);
        if (comisionOptional.isEmpty()) {
            throw new ComisionNoEncontradaExcepcion("La comisión con ID: " + comisionId + " no existe en el sistema");
        }
    }

    private void validarNombreUnico(String nombre, Integer idExcluir) {
        List<TipoTransaccion> tiposExistentes = this.tipoTransaccionRepositorio.findByNombreIgnoreCase(nombre);
        
        if (!tiposExistentes.isEmpty()) {
            // Si se está actualizando, excluir el registro actual de la validación
            if (idExcluir != null) {
                tiposExistentes = tiposExistentes.stream()
                    .filter(tipo -> !tipo.getTipoTransaccionId().equals(idExcluir))
                    .toList();
            }
            
            if (!tiposExistentes.isEmpty()) {
                throw new TipoTransaccionDuplicadaExcepcion("Ya existe un tipo de transacción con el nombre: " + nombre);
            }
        }
    }

    private void validarPuedeBorrarse(Integer id) {
        // Verificar si hay transacciones asociadas a este tipo de transacción
        boolean tieneTransaccionesAsociadas = this.tipoTransaccionRepositorio.existsTransaccionesByTipoTransaccionId(id);
        if (tieneTransaccionesAsociadas) {
            throw new TipoTransaccionInvalidaExcepcion("No se puede eliminar el tipo de transacción porque tiene transacciones asociadas");
        }
    }
} 