package com.banquito.core.aplicacion.cuentas.servicio;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.Cuenta;
import com.banquito.core.aplicacion.cuentas.repositorio.CuentaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class CuentaServicio {

    private static final String ESTADO_ACTIVA = "ACTIVA";
    private static final String ESTADO_INACTIVA = "INACTIVA";
    private static final String ESTADO_BLOQUEADA = "BLOQUEADA";
    private static final String ESTADO_CANCELADA = "CANCELADA";

    private final CuentaRepositorio cuentaRepositorio;

    public CuentaServicio(CuentaRepositorio cuentaRepositorio) {
        this.cuentaRepositorio = cuentaRepositorio;
    }

    public List<Cuenta> listarTodos() {
        List<Cuenta> cuentas = this.cuentaRepositorio.findAll();
        if (cuentas.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("Cuenta", "No existen cuentas registradas");
        }
        return cuentas;
    }

    public Page<Cuenta> listarTodosPaginado(Pageable pageable) {
        Page<Cuenta> page = this.cuentaRepositorio.findAll(pageable);
        if (page.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("Cuenta", "No existen cuentas registradas");
        }
        return page;
    }

    public Cuenta buscarPorId(Integer id) {
        return this.cuentaRepositorio.findById(id)
            .orElseThrow(() -> new EntidadNoEncontradaExcepcion("Cuenta", 
                "No se encontró la cuenta con id: " + id));
    }

    @Transactional
    public Cuenta crear(Cuenta cuenta) {
        try {
            cuenta.setEstado(ESTADO_ACTIVA);
            cuenta.setFechaCreacion(Date.from(Instant.now()));
            cuenta.setFechaModificacion(Date.from(Instant.now()));
            validarCuenta(cuenta);
            if (existeCuentaConCodigo(cuenta.getCodigoCuenta())) {
                throw new CrearEntidadExcepcion("Cuenta", 
                    "Ya existe una cuenta con el código: " + cuenta.getCodigoCuenta());
            }

            return this.cuentaRepositorio.save(cuenta);
        } catch (RuntimeException e) {
            throw new CrearEntidadExcepcion("Cuenta", "Error al crear la cuenta: " + e.getMessage());
        }
    }

    @Transactional
    public Cuenta actualizar(Integer id, Cuenta cuenta) {
        try {
            Cuenta cuentaExistente = buscarPorId(id);
            validarCuenta(cuenta);
            
            if (!cuentaExistente.getCodigoCuenta().equals(cuenta.getCodigoCuenta()) && 
                existeCuentaConCodigo(cuenta.getCodigoCuenta())) {
                throw new ActualizarEntidadExcepcion("Cuenta", 
                    "Ya existe una cuenta con el código: " + cuenta.getCodigoCuenta());
            }

            validarTransicionEstado(cuentaExistente.getEstado(), cuenta.getEstado());
            
            cuentaExistente.setCodigoCuenta(cuenta.getCodigoCuenta());
            cuentaExistente.setEstado(cuenta.getEstado());
            cuentaExistente.setTipoCuenta(cuenta.getTipoCuenta());
            
            return this.cuentaRepositorio.save(cuentaExistente);
        } catch (RuntimeException e) {
            throw new ActualizarEntidadExcepcion("Cuenta", "Error al actualizar la cuenta: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        try {
            Cuenta cuenta = buscarPorId(id);
            if (!cuenta.getEstado().equals(ESTADO_INACTIVA) && !cuenta.getEstado().equals(ESTADO_CANCELADA)) {
                throw new EliminarEntidadExcepcion("Cuenta", 
                    "Solo se pueden eliminar cuentas inactivas o canceladas");
            }
            this.cuentaRepositorio.delete(cuenta);
        } catch (RuntimeException e) {
            throw new EliminarEntidadExcepcion("Cuenta", "Error al eliminar la cuenta: " + e.getMessage());
        }
    }

    // Métodos específicos del negocio
    public List<Cuenta> buscarPorTipoCuenta(Integer idTipoCuenta) {
        List<Cuenta> cuentas = this.cuentaRepositorio.findByTipoCuenta_IdTipoCuenta(idTipoCuenta);
        if (cuentas.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("Cuenta", 
                "No se encontraron cuentas para el tipo: " + idTipoCuenta);
        }
        return cuentas;
    }

    public List<Cuenta> buscarPorEstado(String estado) {
        validarEstado(estado);
        List<Cuenta> cuentas = this.cuentaRepositorio.findByEstado(estado);
        if (cuentas.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("Cuenta", 
                "No se encontraron cuentas en estado: " + estado);
        }
        return cuentas;
    }

    public List<Cuenta> buscarPorTipoCuentaYEstado(Integer idTipoCuenta, String estado) {
        validarEstado(estado);
        List<Cuenta> cuentas = this.cuentaRepositorio.findByTipoCuenta_IdTipoCuentaAndEstado(idTipoCuenta, estado);
        if (cuentas.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("Cuenta", 
                "No se encontraron cuentas del tipo " + idTipoCuenta + " en estado " + estado);
        }
        return cuentas;
    }

    public Cuenta buscarPorCodigoCuenta(String codigoCuenta) {
        if (codigoCuenta == null || codigoCuenta.trim().isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("Cuenta", "El código de cuenta es obligatorio");
        }
        Cuenta cuenta = this.cuentaRepositorio.findByCodigoCuenta(codigoCuenta);
        if (cuenta == null) {
            throw new EntidadNoEncontradaExcepcion("Cuenta", 
                "No se encontró la cuenta con código: " + codigoCuenta);
        }
        return cuenta;
    }

    // Métodos privados de validación
    private void validarCuenta(Cuenta cuenta) {
        if (cuenta.getCodigoCuenta() == null || cuenta.getCodigoCuenta().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("Cuenta", "El código de cuenta es obligatorio");
        }
        if (cuenta.getEstado() == null || cuenta.getEstado().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("Cuenta", "El estado de la cuenta es obligatorio");
        }
        validarEstado(cuenta.getEstado());
        if (cuenta.getTipoCuenta() == null) {
            throw new CrearEntidadExcepcion("Cuenta", "El tipo de cuenta es obligatorio");
        }
    }

    private boolean existeCuentaConCodigo(String codigoCuenta) {
        return this.cuentaRepositorio.findByCodigoCuenta(codigoCuenta) != null;
    }

    private void validarEstado(String estado) {
        if (!ESTADO_ACTIVA.equals(estado) && 
            !ESTADO_INACTIVA.equals(estado) && 
            !ESTADO_BLOQUEADA.equals(estado) && 
            !ESTADO_CANCELADA.equals(estado)) {
            throw new CrearEntidadExcepcion("Cuenta", 
                "El estado debe ser: ACTIVA, INACTIVA, BLOQUEADA o CANCELADA");
        }
    }

    private void validarTransicionEstado(String estadoActual, String nuevoEstado) {
        if (estadoActual.equals(ESTADO_CANCELADA)) {
            throw new ActualizarEntidadExcepcion("Cuenta", 
                "No se puede modificar una cuenta cancelada");
        }
        if (estadoActual.equals(ESTADO_ACTIVA) && nuevoEstado.equals(ESTADO_INACTIVA)) {
            throw new ActualizarEntidadExcepcion("Cuenta", 
                "Una cuenta activa no puede pasar directamente a inactiva");
        }
    }
}
