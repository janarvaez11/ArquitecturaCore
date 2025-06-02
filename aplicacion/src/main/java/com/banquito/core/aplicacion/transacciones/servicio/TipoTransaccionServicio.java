package com.banquito.core.aplicacion.transacciones.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.transacciones.excepcion.TipoTransaccionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.modelo.TipoTransaccion;
import com.banquito.core.aplicacion.transacciones.repositorio.TipoTransaccionRepositorio;

@Service
public class TipoTransaccionServicio {

    private final TipoTransaccionRepositorio tipoTransaccionRepositorio;

    public TipoTransaccionServicio(TipoTransaccionRepositorio tipoTransaccionRepositorio) {
        this.tipoTransaccionRepositorio = tipoTransaccionRepositorio;
    }

    public TipoTransaccion findById(Integer id) {
        if (id == null || id <= 0) {
            throw new TipoTransaccionNoEncontradaExcepcion("El ID del tipo de transacción no puede ser nulo o menor a 1");
        }
        
        Optional<TipoTransaccion> tipoTransaccionOptional = this.tipoTransaccionRepositorio.findById(id);
        if (tipoTransaccionOptional.isPresent()) {
            return tipoTransaccionOptional.get();
        } else {
            throw new TipoTransaccionNoEncontradaExcepcion("El tipo de transacción con ID: " + id + " no existe");
        }
    }

    public List<TipoTransaccion> findAll() {
        List<TipoTransaccion> tipos = this.tipoTransaccionRepositorio.findAll();
        if (tipos.isEmpty()) {
            throw new TipoTransaccionNoEncontradaExcepcion("No existen tipos de transacción registrados");
        }
        return tipos;
    }

    @Transactional
    public TipoTransaccion create(TipoTransaccion tipoTransaccion) {
        if (tipoTransaccion == null) {
            throw new TipoTransaccionNoEncontradaExcepcion("El tipo de transacción no puede ser nulo");
        }

        // Validaciones de reglas de negocio
        if (tipoTransaccion.getNombre() == null || tipoTransaccion.getNombre().trim().isEmpty()) {
            throw new TipoTransaccionNoEncontradaExcepcion("El nombre del tipo de transacción es obligatorio");
        }

        if (tipoTransaccion.getDescripcion() == null || tipoTransaccion.getDescripcion().trim().isEmpty()) {
            throw new TipoTransaccionNoEncontradaExcepcion("La descripción del tipo de transacción es obligatoria");
        }

        if (tipoTransaccion.getComisionId() == null || tipoTransaccion.getComisionId() <= 0) {
            throw new TipoTransaccionNoEncontradaExcepcion("El ID de comisión debe ser válido");
        }

        // Establecer fecha de creación
        tipoTransaccion.setCreadoEn(new java.sql.Timestamp(System.currentTimeMillis()));
        
        return this.tipoTransaccionRepositorio.save(tipoTransaccion);
    }

    @Transactional
    public TipoTransaccion update(Integer id, TipoTransaccion tipoTransaccion) {
        TipoTransaccion tipoExistente = this.findById(id);
        
        if (tipoTransaccion.getNombre() != null && !tipoTransaccion.getNombre().trim().isEmpty()) {
            tipoExistente.setNombre(tipoTransaccion.getNombre());
        }
        
        if (tipoTransaccion.getDescripcion() != null && !tipoTransaccion.getDescripcion().trim().isEmpty()) {
            tipoExistente.setDescripcion(tipoTransaccion.getDescripcion());
        }
        
        if (tipoTransaccion.getComisionId() != null && tipoTransaccion.getComisionId() > 0) {
            tipoExistente.setComisionId(tipoTransaccion.getComisionId());
        }

        return this.tipoTransaccionRepositorio.save(tipoExistente);
    }

    @Transactional
    public void delete(Integer id) {
        TipoTransaccion tipoTransaccion = this.findById(id);
        this.tipoTransaccionRepositorio.delete(tipoTransaccion);
    }
} 