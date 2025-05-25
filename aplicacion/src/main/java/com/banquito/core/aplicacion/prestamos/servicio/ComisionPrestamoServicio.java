package com.banquito.core.aplicacion.prestamos.servicio;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.ComisionPrestamoNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.ComisionPrestamo;
import com.banquito.core.aplicacion.prestamos.repositorio.ComisionPrestamoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ComisionPrestamoServicio {
    
    private final ComisionPrestamoRepositorio repositorio;

    public ComisionPrestamoServicio(ComisionPrestamoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public ComisionPrestamo findById(Integer id) {
        Optional<ComisionPrestamo> ComisionOpcional = this.repositorio.findById(id);
        if (ComisionOpcional.isPresent()) {
            return ComisionOpcional.get();
        } else {
            throw new ComisionPrestamoNoEncontradoExcepcion("Comision Prestamo","Comision Prestamo no se encontrado con ID: " + id);
        }
    }

    @Transactional
    public void create (ComisionPrestamo ComisionPrestamo) {
        try {
            this.repositorio.save(ComisionPrestamo);
        } catch (Exception rte) {
            throw new CrearEntidadExcepcion("Comision Prestamo", "Error al crear la Comision del Prestamo. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void update(ComisionPrestamo ComisionPrestamo) {
        try{
            Optional<ComisionPrestamo> ComisionOpcional = this.repositorio.findById(ComisionPrestamo.getId());

            if (ComisionOpcional.isPresent()) {
                ComisionPrestamo comisionDb = ComisionOpcional.get();
                comisionDb.setNombre(ComisionPrestamo.getNombre());
                comisionDb.setTipoComision(ComisionPrestamo.getTipoComision());
                comisionDb.setTipoCalculo(ComisionPrestamo.getTipoCalculo());
                comisionDb.setValor(ComisionPrestamo.getValor());
                comisionDb.setFechaCreacion(ComisionPrestamo.getFechaCreacion());

                this.repositorio.save(comisionDb);
            } else {
                throw new ComisionPrestamoNoEncontradoExcepcion("ComisionPrestamo", "Error al actualizar la ComisionPrestamo. No se encontró la ComisionPrestamo con ID: " + ComisionPrestamo.getId());
            }
        } catch (Exception rte) {
            throw new ActualizarEntidadExcepcion("ComisionPrestamo", "Error al actualizar la ComisionPrestamo. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<ComisionPrestamo> ComisionOpcional = this.repositorio.findById(id);
            if (ComisionOpcional.isPresent()) {
                this.repositorio.delete(ComisionOpcional.get());
            } else {
                throw new ComisionPrestamoNoEncontradoExcepcion("ComisionPrestamo", "Error al eliminar la ComisionPrestamo. No se encontró la ComisionPrestamo con ID: " + id);
            }
        } catch (Exception rte) {
            throw new EliminarEntidadExcepcion("ComisionPrestamo", "Error al eliminar la ComisionPrestamo. Texto del error: "+rte.getMessage());
        }
    }

}
