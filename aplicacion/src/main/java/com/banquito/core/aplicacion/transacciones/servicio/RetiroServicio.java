package com.banquito.core.aplicacion.transacciones.servicio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.transacciones.excepcion.CuentaInvalidaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.MontoInvalidoExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.TransaccionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.modelo.Retiro;
import com.banquito.core.aplicacion.transacciones.repositorio.RetiroRepositorio;

@Service
public class RetiroServicio {

    private final RetiroRepositorio retiroRepositorio;
    private final TransaccionServicio transaccionServicio;
    private final TipoTransaccionServicio tipoTransaccionServicio;

    // Límites y comisiones para retiros
    private static final BigDecimal MONTO_MINIMO_RETIRO = new BigDecimal("10.00");
    private static final BigDecimal MONTO_MAXIMO_RETIRO_DIARIO = new BigDecimal("5000.00");
    private static final BigDecimal COMISION_RETIRO = new BigDecimal("1.00");

    public RetiroServicio(RetiroRepositorio retiroRepositorio,
                         TransaccionServicio transaccionServicio,
                         TipoTransaccionServicio tipoTransaccionServicio) {
        this.retiroRepositorio = retiroRepositorio;
        this.transaccionServicio = transaccionServicio;
        this.tipoTransaccionServicio = tipoTransaccionServicio;
    }

    public Retiro findById(Integer id) {
        if (id == null || id <= 0) {
            throw new TransaccionNoEncontradaExcepcion("El ID del retiro no puede ser nulo o menor a 1");
        }
        
        Optional<Retiro> retiroOptional = this.retiroRepositorio.findById(id);
        if (retiroOptional.isPresent()) {
            return retiroOptional.get();
        } else {
            throw new TransaccionNoEncontradaExcepcion("El retiro con ID: " + id + " no existe");
        }
    }

    public List<Retiro> findAll() {
        List<Retiro> retiros = this.retiroRepositorio.findAll();
        if (retiros.isEmpty()) {
            throw new TransaccionNoEncontradaExcepcion("No existen retiros registrados");
        }
        return retiros;
    }

    @Transactional
    public Retiro create(Retiro retiro) {
        if (retiro == null) {
            throw new TransaccionNoEncontradaExcepcion("El retiro no puede ser nulo");
        }

        // Validaciones específicas para retiros
        this.validarRetiro(retiro);
        
        // Verificar que la transacción existe
        this.transaccionServicio.findById(retiro.getTransaccionId());
        
        // Verificar que el tipo de transacción existe
        this.tipoTransaccionServicio.findById(retiro.getTipoTransaccionId());

        // Establecer comisión
        retiro.setComision(COMISION_RETIRO);

        return this.retiroRepositorio.save(retiro);
    }

    @Transactional
    public Retiro procesarRetiro(Integer cuentaId, BigDecimal monto, String descripcion) {
        
        // Validar datos de entrada
        this.validarDatosRetiro(cuentaId, monto, descripcion);
        
        // Crear la transacción principal
        com.banquito.core.aplicacion.transacciones.modelo.Transaccion transaccion = 
            new com.banquito.core.aplicacion.transacciones.modelo.Transaccion();
        transaccion.setTipoTransaccionId(2); // Asumir que 2 es retiro
        transaccion.setCuentaId(cuentaId);
        transaccion.setMonto(monto);
        transaccion.setDescripcion(descripcion);
        
        transaccion = this.transaccionServicio.create(transaccion);
        
        // Crear el retiro
        Retiro retiro = new Retiro();
        retiro.setTipoTransaccionId(transaccion.getTipoTransaccionId());
        retiro.setTransaccionId(transaccion.getTransaccionId());
        retiro.setCuentaOrigenId(cuentaId);
        retiro.setComision(COMISION_RETIRO);
        
        return this.retiroRepositorio.save(retiro);
    }

    @Transactional
    public Retiro update(Integer id, Retiro retiro) {
        Retiro retiroExistente = this.findById(id);
        
        // Solo permitir actualizar la comisión
        if (retiro.getComision() != null && retiro.getComision().compareTo(BigDecimal.ZERO) >= 0) {
            retiroExistente.setComision(retiro.getComision());
        }

        return this.retiroRepositorio.save(retiroExistente);
    }

    // Métodos de validación privados
    private void validarRetiro(Retiro retiro) {
        if (retiro.getCuentaOrigenId() == null || retiro.getCuentaOrigenId() <= 0) {
            throw new CuentaInvalidaExcepcion("La cuenta de origen debe ser válida");
        }
        
        if (retiro.getTipoTransaccionId() == null) {
            throw new TransaccionNoEncontradaExcepcion("El tipo de transacción es obligatorio");
        }
        
        if (retiro.getTransaccionId() == null) {
            throw new TransaccionNoEncontradaExcepcion("La transacción es obligatoria");
        }
    }

    private void validarDatosRetiro(Integer cuentaId, BigDecimal monto, String descripcion) {
        if (cuentaId == null || cuentaId <= 0) {
            throw new CuentaInvalidaExcepcion("La cuenta debe ser válida");
        }
        
        if (monto == null) {
            throw new MontoInvalidoExcepcion("El monto del retiro es obligatorio");
        }
        
        if (monto.compareTo(MONTO_MINIMO_RETIRO) < 0) {
            throw new MontoInvalidoExcepcion("El monto mínimo para retiros es: " + MONTO_MINIMO_RETIRO);
        }
        
        if (monto.compareTo(MONTO_MAXIMO_RETIRO_DIARIO) > 0) {
            throw new MontoInvalidoExcepcion("El monto máximo para retiros es: " + MONTO_MAXIMO_RETIRO_DIARIO);
        }
        
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new TransaccionNoEncontradaExcepcion("La descripción del retiro es obligatoria");
        }
    }

    @Transactional
    public void delete(Integer id) {
        Retiro retiro = this.findById(id);
        this.retiroRepositorio.delete(retiro);
    }
} 