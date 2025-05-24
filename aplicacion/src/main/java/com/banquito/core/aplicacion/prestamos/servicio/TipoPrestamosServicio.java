package com.banquito.core.aplicacion.prestamos.servicio;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.TipoProductoNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.TipoPrestamo;
import com.banquito.core.aplicacion.prestamos.repositorio.TipoPrestamoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class TipoPrestamosServicio {
    
    private final TipoPrestamoRepositorio repositorio;

    public TipoPrestamosServicio(TipoPrestamoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public TipoPrestamo findById(Integer id) {
        Optional<TipoPrestamo> tipoOptional = this.repositorio.findById(id);
        if (tipoOptional.isPresent()) {
            return tipoOptional.get();
        } else {
            throw new TipoProductoNoEncontradoExcepcion("Tipos Prestamos","Tipo de préstamo no encontrado con ID: " + id);
        }
    }

    @Transactional
    public void create (TipoPrestamo tipoPrestamo) {
        try {
            this.repositorio.save(tipoPrestamo);
        } catch (Exception rte) {
            throw new CrearEntidadExcepcion("Tipo Prestamos", "Error al crear el Tipo de prestamo. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void update(TipoPrestamo tipoPrestamo) {
        try{
            Optional<TipoPrestamo> tipoOptional = this.repositorio.findById(tipoPrestamo.getIdTipoPrestamo());

            if (tipoOptional.isPresent()) {
                TipoPrestamo tipoPrestamoDb = tipoOptional.get();
                tipoPrestamoDb.setNombre(tipoPrestamo.getNombre());
                tipoPrestamoDb.setDescripcion(tipoPrestamo.getDescripcion());
                tipoPrestamoDb.setMontoMinimo(tipoPrestamo.getMontoMinimo());
                tipoPrestamoDb.setMontoMaximo(tipoPrestamo.getMontoMaximo());
                tipoPrestamoDb.setPlazoMinimo(tipoPrestamo.getPlazoMinimo());
                tipoPrestamoDb.setPlazoMaximo(tipoPrestamo.getPlazoMaximo());
                tipoPrestamoDb.setRequisitos(tipoPrestamo.getRequisitos());
                tipoPrestamoDb.setTipoCliente(tipoPrestamo.getTipoCliente());
                tipoPrestamoDb.setEstado(tipoPrestamo.getEstado());
                tipoPrestamoDb.setFechaModifica(tipoPrestamo.getFechaModifica());

                this.repositorio.save(tipoPrestamoDb);
            } else {
                throw new TipoProductoNoEncontradoExcepcion("Tipo prestamo", "Error al actualizar el Tipo de prestamo. No se encontró el tipo de prestamo con ID: " + tipoPrestamo.getIdTipoPrestamo());
            }
        } catch (Exception rte) {
            throw new ActualizarEntidadExcepcion("Tipo Prestamos", "Error al actualizar el Tipo de prestamo. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<TipoPrestamo> tipoOptional = this.repositorio.findById(id);
            if (tipoOptional.isPresent()) {
                this.repositorio.delete(tipoOptional.get());
            } else {
                throw new TipoProductoNoEncontradoExcepcion("Tipo prestamo", "Error al eliminar el Tipo de prestamo. No se encontró el tipo de prestamo con ID: " + id);
            }
        } catch (Exception rte) {
            throw new EliminarEntidadExcepcion("Tipo Prestamos", "Error al eliminar el Tipo de prestamo. Texto del error: "+rte.getMessage());
        }
    }

}
