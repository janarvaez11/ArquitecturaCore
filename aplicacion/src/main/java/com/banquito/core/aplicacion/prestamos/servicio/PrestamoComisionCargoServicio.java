package com.banquito.core.aplicacion.prestamos.servicio;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.PrestamoComisionCargoExepcion;
import com.banquito.core.aplicacion.prestamos.modelo.PrestamoComisionCargo;
import com.banquito.core.aplicacion.prestamos.modelo.PrestamoComisionCargoId;
import com.banquito.core.aplicacion.prestamos.repositorio.PrestamoComisionCargoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class PrestamoComisionCargoServicio {
    
    private final PrestamoComisionCargoRepositorio repositorio;

    public PrestamoComisionCargoServicio(PrestamoComisionCargoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public PrestamoComisionCargo findById(PrestamoComisionCargoId id) {
        Optional<PrestamoComisionCargo> prestamoComisionCargoOpcional = this.repositorio.findById(id);
        if (prestamoComisionCargoOpcional.isPresent()) {
            return prestamoComisionCargoOpcional.get();
        } else {
            throw new PrestamoComisionCargoExepcion("PrestamoComisionCargo", 
                "Comisión de préstamo no encontrada con ID: " + id);
        }
    }

    @Transactional
    public void create(PrestamoComisionCargo prestamoComisionCargo) {
        try {
            this.repositorio.save(prestamoComisionCargo);
        } catch (Exception rte) {
            throw new CrearEntidadExcepcion("PrestamoComisionCargo", 
                "Error al crear la comisión de préstamo. Texto del error: " + rte.getMessage());
        }
    }

    @Transactional
    public void update(PrestamoComisionCargo prestamoComisionCargo) {
        try {
            Optional<PrestamoComisionCargo> prestamoComisionCargoOpcional = 
                this.repositorio.findById(prestamoComisionCargo.getId());

            if (prestamoComisionCargoOpcional.isPresent()) {
                PrestamoComisionCargo prestamoComisionCargoDb = prestamoComisionCargoOpcional.get();
                prestamoComisionCargoDb.setFechaAsignacion(prestamoComisionCargo.getFechaAsignacion());
                prestamoComisionCargoDb.setPrestamo(prestamoComisionCargo.getPrestamo());

                this.repositorio.save(prestamoComisionCargoDb);
            } else {
                throw new PrestamoComisionCargoExepcion("PrestamoComisionCargo", 
                    "Error al actualizar la comisión de préstamo. No se encontró con ID: " + prestamoComisionCargo.getId());
            }
        } catch (Exception rte) {
            throw new ActualizarEntidadExcepcion("PrestamoComisionCargo", 
                "Error al actualizar la comisión de préstamo. Texto del error: " + rte.getMessage());
        }
    }

    @Transactional
    public void delete(PrestamoComisionCargoId id) {
        try {
            Optional<PrestamoComisionCargo> prestamoComisionCargoOpcional = this.repositorio.findById(id);
            if (prestamoComisionCargoOpcional.isPresent()) {
                this.repositorio.delete(prestamoComisionCargoOpcional.get());
            } else {
                throw new PrestamoComisionCargoExepcion("PrestamoComisionCargo", 
                    "Error al eliminar la comisión de préstamo. No se encontró con ID: " + id);
            }
        } catch (Exception rte) {
            throw new EliminarEntidadExcepcion("PrestamoComisionCargo", 
                "Error al eliminar la comisión de préstamo. Texto del error: " + rte.getMessage());
        }
    }
} 