package com.banquito.core.aplicacion.transacciones.servicio;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.transacciones.excepcion.CuentaInvalidaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.MontoInvalidoExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.RetiroInvalidoExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.RetiroNoEncontradoExcepcion;
import com.banquito.core.aplicacion.transacciones.modelo.Retiro;
import com.banquito.core.aplicacion.transacciones.modelo.Transaccion;
import com.banquito.core.aplicacion.transacciones.repositorio.RetiroRepositorio;

@Service
public class RetiroServicio {

    private final RetiroRepositorio retiroRepositorio;
    private final TransaccionServicio transaccionServicio;

    private static final BigDecimal COMISION_RETIRO = new BigDecimal("2.50");
    private static final BigDecimal MONTO_MINIMO_RETIRO = new BigDecimal("10.00");
    private static final BigDecimal MONTO_MAXIMO_RETIRO = new BigDecimal("5000.00");

    public RetiroServicio(RetiroRepositorio retiroRepositorio, TransaccionServicio transaccionServicio) {
        this.retiroRepositorio = retiroRepositorio;
        this.transaccionServicio = transaccionServicio;
    }

    @Transactional(readOnly = true)
    public Retiro findById(Integer id) {
        if (id == null || id <= 0) {
            throw new RetiroInvalidoExcepcion("El ID del retiro debe ser un número entero positivo");
        }
        
        Optional<Retiro> retiroOptional = this.retiroRepositorio.findById(id);
        if (retiroOptional.isPresent()) {
            return retiroOptional.get();
        } else {
            throw new RetiroNoEncontradoExcepcion("El retiro con ID: " + id + " no existe");
        }
    }

    @Transactional(readOnly = true)
    public List<Retiro> findAll() {
        List<Retiro> retiros = this.retiroRepositorio.findAll();
        if (retiros.isEmpty()) {
            throw new RetiroNoEncontradoExcepcion("No existen retiros registrados");
        }
        return retiros;
    }

    @Transactional
    public Retiro create(Retiro retiro) {
        if (retiro == null) {
            throw new RetiroInvalidoExcepcion("El retiro no puede ser nulo");
        }

        this.validarRetiroParaCreacion(retiro);
        
        this.transaccionServicio.findById(retiro.getTransaccionId());
        
        this.validarTipoTransaccion(retiro.getTipoTransaccionId());
        
        retiro.setComision(COMISION_RETIRO);
        
        try {
            return this.retiroRepositorio.save(retiro);
        } catch (Exception e) {
            throw new RetiroInvalidoExcepcion("Error al crear el retiro: " + e.getMessage());
        }
    }

    @Transactional
    public Retiro createRetiroCompleto(BigDecimal monto, Integer cuentaId, String descripcion) {
        this.validarDatosEntrada(monto, cuentaId, descripcion);
        
        Transaccion transaccion = new Transaccion();
        transaccion.setMonto(monto);
        transaccion.setCuentaId(cuentaId);
        transaccion.setTipoTransaccionId(2);
        transaccion.setDescripcion(descripcion);
        transaccion.setFechaTransaccion(new Date(System.currentTimeMillis()));
        transaccion.setFechaContable(new Date(System.currentTimeMillis()));
        transaccion.setCreadoEn(new Timestamp(System.currentTimeMillis()));
        
        transaccion = this.transaccionServicio.create(transaccion);
        
        Retiro retiro = new Retiro();
        retiro.setTransaccionId(transaccion.getTransaccionId());
        retiro.setTipoTransaccionId(transaccion.getTipoTransaccionId());
        retiro.setCuentaOrigenId(cuentaId);
        retiro.setComision(COMISION_RETIRO);
        
        return this.retiroRepositorio.save(retiro);
    }

    @Transactional
    public Retiro update(Integer id, Retiro retiro) {
        Retiro retiroExistente = this.findById(id);
        
        if (retiro.getComision() != null && retiro.getComision().compareTo(BigDecimal.ZERO) > 0) {
            retiroExistente.setComision(retiro.getComision());
        }
        
        return this.retiroRepositorio.save(retiroExistente);
    }

    private void validarRetiroParaCreacion(Retiro retiro) {
        if (retiro.getTransaccionId() == null || retiro.getTransaccionId() <= 0) {
            throw new RetiroInvalidoExcepcion("El ID de la transacción debe ser válido");
        }
        
        if (retiro.getTipoTransaccionId() == null || retiro.getTipoTransaccionId() <= 0) {
            throw new RetiroInvalidoExcepcion("El ID del tipo de transacción debe ser válido");
        }
        
        if (retiro.getCuentaOrigenId() == null || retiro.getCuentaOrigenId() <= 0) {
            throw new RetiroInvalidoExcepcion("El ID de la cuenta origen debe ser válido");
        }
    }

    private void validarDatosEntrada(BigDecimal monto, Integer cuentaId, String descripcion) {
        if (monto == null) {
            throw new MontoInvalidoExcepcion("El monto del retiro es obligatorio");
        }
        
        if (monto.compareTo(MONTO_MINIMO_RETIRO) < 0) {
            throw new MontoInvalidoExcepcion("El monto mínimo para retiro es: " + MONTO_MINIMO_RETIRO);
        }
        
        if (monto.compareTo(MONTO_MAXIMO_RETIRO) > 0) {
            throw new MontoInvalidoExcepcion("El monto máximo para retiro es: " + MONTO_MAXIMO_RETIRO);
        }
        
        if (cuentaId == null || cuentaId <= 0) {
            throw new CuentaInvalidaExcepcion("El ID de la cuenta debe ser válido");
        }
        
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new RetiroInvalidoExcepcion("La descripción del retiro es obligatoria");
        }
    }

    private void validarTipoTransaccion(Integer tipoTransaccionId) {
        if (!Integer.valueOf(2).equals(tipoTransaccionId)) {
            throw new RetiroInvalidoExcepcion("El tipo de transacción debe corresponder a retiro");
        }
    }

    @Transactional
    public void delete(Integer id) {
        Retiro retiro = this.findById(id);
        this.retiroRepositorio.delete(retiro);
    }
} 