package com.banquito.core.aplicacion.prestamos.servicio;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CondicionComisionNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.CondicionComision;
import com.banquito.core.aplicacion.prestamos.repositorio.CondicionComisionRepositorio;

import jakarta.transaction.Transactional;

@Service
public class CondicionComisionServicio {
    
    private final CondicionComisionRepositorio repositorio;

    public CondicionComisionServicio(CondicionComisionRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public CondicionComision findById(Integer id) {
        Optional<CondicionComision> CondicionOpcional = this.repositorio.findById(id);
        if (CondicionOpcional.isPresent()) {
            return CondicionOpcional.get();
        } else {
            throw new CondicionComisionNoEncontradoExcepcion("Condicion Comision","Condicion Comision no se encontrado con ID: " + id);
        }
    }

    @Transactional
    public void create (CondicionComision CondicionComision) {
        try {
            this.repositorio.save(CondicionComision);
        } catch (Exception rte) {
            throw new CrearEntidadExcepcion("Condicion Comision", "Error al crear la Condicion de la Comision. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void update(CondicionComision CondicionComision) {
        try{
            Optional<CondicionComision> CondicionOpcional = this.repositorio.findById(CondicionComision.getId());

            if (CondicionOpcional.isPresent()) {
                CondicionComision comisionDb = CondicionOpcional.get();
                comisionDb.setComisionPrestamo(CondicionComision.getComisionPrestamo());
                comisionDb.setTipoCondicion(CondicionComision.getTipoCondicion());
                comisionDb.setValor(CondicionComision.getValor());

                this.repositorio.save(comisionDb);
            } else {
                throw new CondicionComisionNoEncontradoExcepcion("Condicion Comision", "Error al actualizar la Condicion Comision. No se encontró la Condicion Comision con ID: " + CondicionComision.getId());
            }
        } catch (Exception rte) {
            throw new ActualizarEntidadExcepcion("Condicion Comision", "Error al actualizar la Condicion Comision. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<CondicionComision> CondicionOpcional = this.repositorio.findById(id);
            if (CondicionOpcional.isPresent()) {
                this.repositorio.delete(CondicionOpcional.get());
            } else {
                throw new CondicionComisionNoEncontradoExcepcion("Condicion Comision", "Error al eliminar la Condicion Comision. No se encontró la Condicion Comision con ID: " + id);
            }
        } catch (Exception rte) {
            throw new EliminarEntidadExcepcion("Condicion Comision", "Error al eliminar la Condicion Comision. Texto del error: "+rte.getMessage());
        }
    }

}
