package com.banquito.core.aplicacion.prestamos.servicio;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.PrestamoNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.Prestamo;
import com.banquito.core.aplicacion.prestamos.repositorio.PrestamoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class PrestamoServicio {
    
    private final PrestamoRepositorio repositorio;

    public PrestamoServicio(PrestamoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Prestamo findById(Integer id) {
        Optional<Prestamo> prestamoOpcional = this.repositorio.findById(id);
        if (prestamoOpcional.isPresent()) {
            return prestamoOpcional.get();
        } else {
            throw new PrestamoNoEncontradoExcepcion("Prestamos","Tipo de préstamo no encontrado con ID: " + id);
        }
    }

    @Transactional
    public void create (Prestamo tipoPrestamo) {
        try {
            this.repositorio.save(tipoPrestamo);
        } catch (Exception rte) {
            throw new CrearEntidadExcepcion("Prestamos", "Error al crear el prestamo. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void update(Prestamo tipoPrestamo) {
        try{
            Optional<Prestamo> prestamoOpcional = this.repositorio.findById(tipoPrestamo.getId());

            if (prestamoOpcional.isPresent()) {
                Prestamo prestamoDb = prestamoOpcional.get();
                prestamoDb.setTipoPrestamo(tipoPrestamo.getTipoPrestamo());
                prestamoDb.setNombre(tipoPrestamo.getNombre());
                prestamoDb.setDescripcion(tipoPrestamo.getDescripcion());
                prestamoDb.setEstado(tipoPrestamo.getEstado());
                prestamoDb.setFechaModificacion(tipoPrestamo.getFechaModificacion());
                prestamoDb.setBaseCalculo(tipoPrestamo.getBaseCalculo());
                prestamoDb.setTasaMonetaria(tipoPrestamo.getTasaMonetaria());

                this.repositorio.save(prestamoDb);
            } else {
                throw new PrestamoNoEncontradoExcepcion("Prestamo", "Error al actualizar el prestamo. No se encontró el tipo de prestamo con ID: " + tipoPrestamo.getId());
            }
        } catch (Exception rte) {
            throw new ActualizarEntidadExcepcion("Prestamos", "Error al actualizar el prestamo. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<Prestamo> prestamoOpcional = this.repositorio.findById(id);
            if (prestamoOpcional.isPresent()) {
                this.repositorio.delete(prestamoOpcional.get());
            } else {
                throw new PrestamoNoEncontradoExcepcion("Prestamo", "Error al eliminar el prestamo. No se encontró el tipo de prestamo con ID: " + id);
            }
        } catch (Exception rte) {
            throw new EliminarEntidadExcepcion("Prestamos", "Error al eliminar el prestamo. Texto del error: "+rte.getMessage());
        }
    }

}
