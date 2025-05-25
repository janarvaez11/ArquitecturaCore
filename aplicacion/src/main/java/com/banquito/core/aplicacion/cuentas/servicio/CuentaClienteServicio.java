package com.banquito.core.aplicacion.cuentas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CuentaClienteNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.CuentaCliente;
import com.banquito.core.aplicacion.cuentas.repositorio.CuentaClienteRepositorio;

import jakarta.transaction.Transactional;

@Service
public class CuentaClienteServicio {

    private final CuentaClienteRepositorio cuentaClienteRepositorio;

    public CuentaClienteServicio(CuentaClienteRepositorio cuentaClienteRepositorio) {
        this.cuentaClienteRepositorio = cuentaClienteRepositorio;
    }

    public List<CuentaCliente> findAll() {
        return this.cuentaClienteRepositorio.findAll();
    }

    public CuentaCliente findById(Integer id) {
        Optional<CuentaCliente> cuentaClienteOptional = this.cuentaClienteRepositorio.findById(id);
        if (cuentaClienteOptional.isPresent()) {
            return cuentaClienteOptional.get();
        } else {
            throw new CuentaClienteNoEncontradaExcepcion("CuentaCliente", "No se encontró la cuenta del cliente con ID: " + id);
        }
    }

    @Transactional
    public void create(CuentaCliente cuentaCliente) {
        try {
            this.cuentaClienteRepositorio.save(cuentaCliente);
        } catch (Exception e) {
            throw new CrearEntidadExcepcion("CuentaCliente", "Error al crear la cuenta del cliente. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void update(CuentaCliente cuentaCliente) {
        try {
            Optional<CuentaCliente> cuentaClienteOptional = this.cuentaClienteRepositorio.findById(cuentaCliente.getIdCuentaCliente());
            if (cuentaClienteOptional.isPresent()) {
                CuentaCliente cuentaClienteDB = cuentaClienteOptional.get();
                cuentaClienteDB.setIdCliente(cuentaCliente.getIdCliente());
                cuentaClienteDB.setIdCuenta(cuentaCliente.getIdCuenta());
                cuentaClienteDB.setEstado(cuentaCliente.getEstado());
                cuentaClienteDB.setFechaApertura(cuentaCliente.getFechaApertura());
                this.cuentaClienteRepositorio.save(cuentaClienteDB);
            } else {
                throw new CuentaClienteNoEncontradaExcepcion("CuentaCliente", "No se encontró la cuenta del cliente con ID: " + cuentaCliente.getIdCuentaCliente());
            }
        } catch (Exception e) {
            throw new ActualizarEntidadExcepcion("CuentaCliente", "Error al actualizar la cuenta del cliente. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<CuentaCliente> cuentaClienteOptional = this.cuentaClienteRepositorio.findById(id);
            if (cuentaClienteOptional.isPresent()) {
                this.cuentaClienteRepositorio.delete(cuentaClienteOptional.get());
            } else {
                throw new CuentaClienteNoEncontradaExcepcion("CuentaCliente", "No se encontró la cuenta del cliente con ID: " + id);
            }
        } catch (Exception e) {
            throw new EliminarEntidadExcepcion("CuentaCliente", "Error al eliminar la cuenta del cliente. Error: " + e.getMessage());
        }
    }
}
