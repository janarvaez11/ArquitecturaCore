package com.banquito.core.aplicacion.cuentas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.ComisionCargoNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.ComisionCargo;
import com.banquito.core.aplicacion.cuentas.repositorio.ComisionCargoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ComisionCargoServicio {

    private final ComisionCargoRepositorio comisionCargoRepositorio;

    public ComisionCargoServicio(ComisionCargoRepositorio comisionCargoRepositorio) {
        this.comisionCargoRepositorio = comisionCargoRepositorio;
    }

    public List<ComisionCargo> findAll() {
        return this.comisionCargoRepositorio.findAll();
    }

    public ComisionCargo findById(Integer id) {
        Optional<ComisionCargo> comisionCargoOptional = this.comisionCargoRepositorio.findById(id);
        if (comisionCargoOptional.isPresent()) {
            return comisionCargoOptional.get();
        } else {
            throw new ComisionCargoNoEncontradaExcepcion("ComisionCargo", "No se encontró la comisión/cargo con ID: " + id);
        }
    }

    @Transactional
    public void create(ComisionCargo comisionCargo) {
        try {
            this.comisionCargoRepositorio.save(comisionCargo);
        } catch (Exception e) {
            throw new CrearEntidadExcepcion("ComisionCargo", "Error al crear la comisión/cargo. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void update(ComisionCargo comisionCargo) {
        try {
            Optional<ComisionCargo> comisionCargoOptional = this.comisionCargoRepositorio.findById(comisionCargo.getIdComisionCargo());
            if (comisionCargoOptional.isPresent()) {
                ComisionCargo comisionCargoDB = comisionCargoOptional.get();
                comisionCargoDB.setNombre(comisionCargo.getNombre());
                comisionCargoDB.setBaseCalculo(comisionCargo.getBaseCalculo());
                comisionCargoDB.setMonto(comisionCargo.getMonto());
                comisionCargoDB.setTipoComision(comisionCargo.getTipoComision());
                comisionCargoDB.setFrecuencia(comisionCargo.getFrecuencia());
                this.comisionCargoRepositorio.save(comisionCargoDB);
            } else {
                throw new ComisionCargoNoEncontradaExcepcion("ComisionCargo", "No se encontró la comisión/cargo con ID: " + comisionCargo.getIdComisionCargo());
            }
        } catch (Exception e) {
            throw new ActualizarEntidadExcepcion("ComisionCargo", "Error al actualizar la comisión/cargo. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<ComisionCargo> comisionCargoOptional = this.comisionCargoRepositorio.findById(id);
            if (comisionCargoOptional.isPresent()) {
                this.comisionCargoRepositorio.delete(comisionCargoOptional.get());
            } else {
                throw new ComisionCargoNoEncontradaExcepcion("ComisionCargo", "No se encontró la comisión/cargo con ID: " + id);
            }
        } catch (Exception e) {
            throw new EliminarEntidadExcepcion("ComisionCargo", "Error al eliminar la comisión/cargo. Error: " + e.getMessage());
        }
    }
} 