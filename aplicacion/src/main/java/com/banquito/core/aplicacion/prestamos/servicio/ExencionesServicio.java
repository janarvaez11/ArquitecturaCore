package com.banquito.core.aplicacion.prestamos.servicio;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.ExencionesPrestamoNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.ExencionesPrestamo;
import com.banquito.core.aplicacion.prestamos.repositorio.ExencionesPrestamoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ExencionesServicio {
    
    private final ExencionesPrestamoRepositorio repositorio;

    public ExencionesServicio(ExencionesPrestamoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public ExencionesPrestamo findById(Integer id) {
        Optional<ExencionesPrestamo> ExencionOpcional = this.repositorio.findById(id);
        if (ExencionOpcional.isPresent()) {
            return ExencionOpcional.get();
        } else {
            throw new ExencionesPrestamoNoEncontradoExcepcion("Exenciones Prestamo","Exenciones Prestamo no encontrado con ID: " + id);
        }
    }

    @Transactional
    public void create (ExencionesPrestamo ExencionesPrestamo) {
        try {
            this.repositorio.save(ExencionesPrestamo);
        } catch (Exception rte) {
            throw new CrearEntidadExcepcion("Exenciones Prestamo", "Error al crear la Exencion Prestamo. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void update(ExencionesPrestamo ExencionesPrestamo) {
        try{
            Optional<ExencionesPrestamo> ExencionOpcional = this.repositorio.findById(ExencionesPrestamo.getId());

            if (ExencionOpcional.isPresent()) {
                ExencionesPrestamo exencionDb = ExencionOpcional.get();
                exencionDb.setTipoExencion(ExencionesPrestamo.getTipoExencion());
                exencionDb.setNombre(ExencionesPrestamo.getNombre());
                exencionDb.setDescripcion(ExencionesPrestamo.getDescripcion());
                
                this.repositorio.save(exencionDb);
            } else {
                throw new ExencionesPrestamoNoEncontradoExcepcion("Exenciones Prestamo", "Error al actualizar la Exencion Prestamo. No se encontró la ExencionesPrestamo con ID: " + ExencionesPrestamo.getId());
            }
        } catch (Exception rte) {
            throw new ActualizarEntidadExcepcion("Exenciones Prestamo", "Error al actualizar la Exencion Prestamo. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<ExencionesPrestamo> ExencionOpcional = this.repositorio.findById(id);
            if (ExencionOpcional.isPresent()) {
                this.repositorio.delete(ExencionOpcional.get());
            } else {
                throw new ExencionesPrestamoNoEncontradoExcepcion("Exenciones Prestamo", "Error al eliminar la ExencionesPrestamo. No se encontró la ExencionesPrestamo con ID: " + id);
            }
        } catch (Exception rte) {
            throw new EliminarEntidadExcepcion("Exenciones Prestamo", "Error al eliminar la ExencionesPrestamo. Texto del error: "+rte.getMessage());
        }
    }

}
