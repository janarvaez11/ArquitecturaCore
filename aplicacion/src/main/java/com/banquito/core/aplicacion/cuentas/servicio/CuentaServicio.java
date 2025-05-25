package com.banquito.core.aplicacion.cuentas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CuentaNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.Cuenta;
import com.banquito.core.aplicacion.cuentas.repositorio.CuentaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class CuentaServicio {

    private final CuentaRepositorio cuentaRepositorio;

    public CuentaServicio(CuentaRepositorio cuentaRepositorio) {
        this.cuentaRepositorio = cuentaRepositorio;
    }

    public List<Cuenta> findAll() {
        return this.cuentaRepositorio.findAll();
    }

    public Cuenta findById(Integer id) {
        Optional<Cuenta> cuentaOptional = this.cuentaRepositorio.findById(id);
        if (cuentaOptional.isPresent()) {
            return cuentaOptional.get();
        } else {
            throw new CuentaNoEncontradaExcepcion("Cuenta", "No se encontró la cuenta con ID: " + id);
        }
    }

    @Transactional
    public void create(Cuenta cuenta) {
        try {
            this.cuentaRepositorio.save(cuenta);
        } catch (Exception e) {
            throw new CrearEntidadExcepcion("Cuenta", "Error al crear la cuenta. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void update(Cuenta cuenta) {
        try {
            Optional<Cuenta> cuentaOptional = this.cuentaRepositorio.findById(cuenta.getIdCuenta());
            if (cuentaOptional.isPresent()) {
                Cuenta cuentaDB = cuentaOptional.get();
                cuentaDB.setNombre(cuenta.getNombre());
                cuentaDB.setDescripcion(cuenta.getDescripcion());
                cuentaDB.setEstado(cuenta.getEstado());
                cuentaDB.setIdTipoCuenta(cuenta.getIdTipoCuenta());
                cuentaDB.setIdTasaInteres(cuenta.getIdTasaInteres());
                cuentaDB.setFechaModificacion(cuenta.getFechaModificacion());
                this.cuentaRepositorio.save(cuentaDB);
            } else {
                throw new CuentaNoEncontradaExcepcion("Cuenta", "No se encontró la cuenta con ID: " + cuenta.getIdCuenta());
            }
        } catch (Exception e) {
            throw new ActualizarEntidadExcepcion("Cuenta", "Error al actualizar la cuenta. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<Cuenta> cuentaOptional = this.cuentaRepositorio.findById(id);
            if (cuentaOptional.isPresent()) {
                this.cuentaRepositorio.delete(cuentaOptional.get());
            } else {
                throw new CuentaNoEncontradaExcepcion("Cuenta", "No se encontró la cuenta con ID: " + id);
            }
        } catch (Exception e) {
            throw new EliminarEntidadExcepcion("Cuenta", "Error al eliminar la cuenta. Error: " + e.getMessage());
        }
    }
}
