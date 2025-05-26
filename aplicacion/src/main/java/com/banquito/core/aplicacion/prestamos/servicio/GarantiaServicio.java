package com.banquito.core.aplicacion.prestamos.servicio;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.GarantiaNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.Garantia;
import com.banquito.core.aplicacion.prestamos.repositorio.GarantiaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class GarantiaServicio {
    
    private final GarantiaRepositorio repositorio;

    public GarantiaServicio(GarantiaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Garantia findById(Integer id) {
        Optional<Garantia> garantiaOpcional = this.repositorio.findById(id);
        if (garantiaOpcional.isPresent()) {
            return garantiaOpcional.get();
        } else {
            throw new GarantiaNoEncontradoExcepcion("Garantia","Garantia no encontrado con ID: " + id);
        }
    }

    @Transactional
    public void create (Garantia Garantia) {
        try {
            this.repositorio.save(Garantia);
        } catch (Exception rte) {
            throw new CrearEntidadExcepcion("Garantia", "Error al crear la Garantia. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void update(Garantia Garantia) {
        try{
            Optional<Garantia> garantiaOpcional = this.repositorio.findById(Garantia.getId());

            if (garantiaOpcional.isPresent()) {
                Garantia garantiaDb = garantiaOpcional.get();
                garantiaDb.setDescripcion(Garantia.getDescripcion());
                garantiaDb.setValor(Garantia.getValor());
                garantiaDb.setTipoGarantia(Garantia.getTipoGarantia());

                this.repositorio.save(garantiaDb);
            } else {
                throw new GarantiaNoEncontradoExcepcion("Garantia", "Error al actualizar la Garantia. No se encontró la Garantia con ID: " + Garantia.getId());
            }
        } catch (Exception rte) {
            throw new ActualizarEntidadExcepcion("Garantia", "Error al actualizar la Garantia. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<Garantia> garantiaOpcional = this.repositorio.findById(id);
            if (garantiaOpcional.isPresent()) {
                this.repositorio.delete(garantiaOpcional.get());
            } else {
                throw new GarantiaNoEncontradoExcepcion("Garantia", "Error al eliminar la Garantia. No se encontró la Garantia con ID: " + id);
            }
        } catch (Exception rte) {
            throw new EliminarEntidadExcepcion("Garantia", "Error al eliminar la Garantia. Texto del error: "+rte.getMessage());
        }
    }

}
