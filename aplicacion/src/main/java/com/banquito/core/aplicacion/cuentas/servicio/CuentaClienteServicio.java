package com.banquito.core.aplicacion.cuentas.servicio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CuentaClienteNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.CuentaCliente;
import com.banquito.core.aplicacion.cuentas.modelo.TipoCuenta;
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
                
                // Validación de saldo usando el tipo de cuenta
                if (cuentaCliente.getSaldoDisponible() != null && 
                    cuentaCliente.getCuenta() != null &&
                    cuentaCliente.getCuenta().getTipoCuenta() != null) {
                    TipoCuenta tipoCuenta = cuentaCliente.getCuenta().getTipoCuenta();
                    if (tipoCuenta.getTasaInteres() != null && 
                        tipoCuenta.getTasaInteres().getBaseCalculo() != null) {
                        // Si el tipo de cuenta tiene un saldo mínimo definido en su base de cálculo
                        if (cuentaCliente.getSaldoDisponible().compareTo(BigDecimal.ZERO) < 0) {
                            throw new ActualizarEntidadExcepcion("CuentaCliente", 
                                "El saldo disponible no puede ser negativo");
                        }
                    }
                }
                
                // Actualización con manejo de concurrencia usando @Version
                cuentaClienteDB.setCliente(cuentaCliente.getCliente());
                cuentaClienteDB.setCuenta(cuentaCliente.getCuenta());
                cuentaClienteDB.setEstado(cuentaCliente.getEstado());
                cuentaClienteDB.setFechaApertura(cuentaCliente.getFechaApertura());
                cuentaClienteDB.setSaldoDisponible(cuentaCliente.getSaldoDisponible());
                cuentaClienteDB.setSaldoContable(cuentaCliente.getSaldoContable());
                this.cuentaClienteRepositorio.save(cuentaClienteDB);
            } else {
                throw new CuentaClienteNoEncontradaExcepcion("CuentaCliente", 
                    "No se encontró la cuenta del cliente con ID: " + cuentaCliente.getIdCuentaCliente());
            }
        } catch (Exception e) {
            throw new ActualizarEntidadExcepcion("CuentaCliente", 
                "Error al actualizar la cuenta del cliente. Error: " + e.getMessage());
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

    public List<CuentaCliente> findByClienteId(Integer idCliente) {
        return this.cuentaClienteRepositorio.findByClienteId(idCliente);
    }

    public List<CuentaCliente> findByEstado(String estado) {
        return this.cuentaClienteRepositorio.findByEstado(estado);
    }

    public List<CuentaCliente> findBySaldoDisponibleGreaterThan(Double saldoMinimo) {
        return this.cuentaClienteRepositorio.findBySaldoDisponibleGreaterThan(saldoMinimo);
    }

    public CuentaCliente findByNumeroCuenta(String numeroCuenta) {
        return this.cuentaClienteRepositorio.findByNumeroCuenta(numeroCuenta);
    }
}
