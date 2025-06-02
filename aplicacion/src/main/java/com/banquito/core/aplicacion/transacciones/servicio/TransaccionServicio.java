package com.banquito.core.aplicacion.transacciones.servicio;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.transacciones.excepcion.CuentaInvalidaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.EstadoTransaccionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.MontoInvalidoExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.TipoTransaccionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.TransaccionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.modelo.Transaccion;
import com.banquito.core.aplicacion.transacciones.repositorio.TransaccionRepositorio;

@Service
public class TransaccionServicio {

    private final TransaccionRepositorio transaccionRepositorio;
    private final TipoTransaccionServicio tipoTransaccionServicio;
    private final EstadoTransaccionServicio estadoTransaccionServicio;

    private static final BigDecimal MONTO_MINIMO = new BigDecimal("0.01");
    private static final BigDecimal MONTO_MAXIMO_DIARIO = new BigDecimal("10000.00");
    private static final String ESTADO_INICIAL = "PEN";

    public TransaccionServicio(TransaccionRepositorio transaccionRepositorio,
                              TipoTransaccionServicio tipoTransaccionServicio,
                              EstadoTransaccionServicio estadoTransaccionServicio) {
        this.transaccionRepositorio = transaccionRepositorio;
        this.tipoTransaccionServicio = tipoTransaccionServicio;
        this.estadoTransaccionServicio = estadoTransaccionServicio;
    }

    public Transaccion findById(Integer id) {
        if (id == null || id <= 0) {
            throw new TransaccionNoEncontradaExcepcion("El ID de la transacción no puede ser nulo o menor a 1");
        }
        
        Optional<Transaccion> transaccionOptional = this.transaccionRepositorio.findById(id);
        if (transaccionOptional.isPresent()) {
            return transaccionOptional.get();
        } else {
            throw new TransaccionNoEncontradaExcepcion("La transacción con ID: " + id + " no existe");
        }
    }

    public List<Transaccion> findAll() {
        List<Transaccion> transacciones = this.transaccionRepositorio.findAll();
        if (transacciones.isEmpty()) {
            throw new TransaccionNoEncontradaExcepcion("No existen transacciones registradas");
        }
        return transacciones;
    }

    @Transactional
    public Transaccion create(Transaccion transaccion) {
        if (transaccion == null) {
            throw new TransaccionNoEncontradaExcepcion("La transacción no puede ser nula");
        }

        this.validarDatosBasicos(transaccion);
        this.validarMonto(transaccion.getMonto());
        this.validarCuenta(transaccion.getCuentaId());
        this.validarTipoTransaccion(transaccion.getTipoTransaccionId());
        this.validarFechas(transaccion);

        transaccion.setEstadoId(ESTADO_INICIAL);
        transaccion.setCreadoEn(new Timestamp(System.currentTimeMillis()));
        
        if (transaccion.getFechaTransaccion() == null) {
            transaccion.setFechaTransaccion(new Date(System.currentTimeMillis()));
        }
        
        if (transaccion.getFechaContable() == null) {
            transaccion.setFechaContable(new Date(System.currentTimeMillis()));
        }

        return this.transaccionRepositorio.save(transaccion);
    }

    @Transactional
    public Transaccion update(Integer id, Transaccion transaccion) {
        Transaccion transaccionExistente = this.findById(id);
        
        if (transaccion.getDescripcion() != null && !transaccion.getDescripcion().trim().isEmpty()) {
            transaccionExistente.setDescripcion(transaccion.getDescripcion());
        }

        if (transaccion.getEstadoId() != null) {
            this.validarCambioEstado(transaccionExistente.getEstadoId(), transaccion.getEstadoId());
            transaccionExistente.setEstadoId(transaccion.getEstadoId());
        }

        return this.transaccionRepositorio.save(transaccionExistente);
    }

    @Transactional
    public Transaccion completarTransaccion(Integer id) {
        Transaccion transaccion = this.findById(id);
        
        if (!"PEN".equals(transaccion.getEstadoId())) {
            throw new EstadoTransaccionNoEncontradaExcepcion("Solo se pueden completar transacciones en estado pendiente");
        }

        transaccion.setEstadoId("COM");
        return this.transaccionRepositorio.save(transaccion);
    }

    @Transactional
    public Transaccion cancelarTransaccion(Integer id) {
        Transaccion transaccion = this.findById(id);
        
        if (!"PEN".equals(transaccion.getEstadoId())) {
            throw new EstadoTransaccionNoEncontradaExcepcion("Solo se pueden cancelar transacciones en estado pendiente");
        }

        transaccion.setEstadoId("CAN");
        return this.transaccionRepositorio.save(transaccion);
    }

    private void validarDatosBasicos(Transaccion transaccion) {
        if (transaccion.getDescripcion() == null || transaccion.getDescripcion().trim().isEmpty()) {
            throw new TransaccionNoEncontradaExcepcion("La descripción de la transacción es obligatoria");
        }
    }

    private void validarMonto(BigDecimal monto) {
        if (monto == null) {
            throw new MontoInvalidoExcepcion("El monto de la transacción es obligatorio");
        }
        
        if (monto.compareTo(MONTO_MINIMO) < 0) {
            throw new MontoInvalidoExcepcion("El monto mínimo de transacción es: " + MONTO_MINIMO);
        }
        
        if (monto.compareTo(MONTO_MAXIMO_DIARIO) > 0) {
            throw new MontoInvalidoExcepcion("El monto excede el límite diario de: " + MONTO_MAXIMO_DIARIO);
        }
    }

    private void validarCuenta(Integer cuentaId) {
        if (cuentaId == null || cuentaId <= 0) {
            throw new CuentaInvalidaExcepcion("El ID de la cuenta debe ser válido");
        }
    }

    private void validarTipoTransaccion(Integer tipoTransaccionId) {
        if (tipoTransaccionId == null) {
            throw new TipoTransaccionNoEncontradaExcepcion("El tipo de transacción es obligatorio");
        }
        
        this.tipoTransaccionServicio.findById(tipoTransaccionId);
    }

    private void validarFechas(Transaccion transaccion) {
        Date fechaActual = new Date(System.currentTimeMillis());
        
        if (transaccion.getFechaTransaccion() != null && 
            transaccion.getFechaTransaccion().after(fechaActual)) {
            throw new TransaccionNoEncontradaExcepcion("La fecha de transacción no puede ser futura");
        }
        
        if (transaccion.getFechaContable() != null && 
            transaccion.getFechaContable().after(fechaActual)) {
            throw new TransaccionNoEncontradaExcepcion("La fecha contable no puede ser futura");
        }
    }

    private void validarCambioEstado(String estadoActual, String nuevoEstado) {
        if (!this.estadoTransaccionServicio.puedeTransicionarA(estadoActual, nuevoEstado)) {
            throw new EstadoTransaccionNoEncontradaExcepcion(
                "No se puede cambiar del estado " + estadoActual + " al estado " + nuevoEstado);
        }
    }

    @Transactional
    public void delete(Integer id) {
        Transaccion transaccion = this.findById(id);
        
        if ("COM".equals(transaccion.getEstadoId())) {
            throw new TransaccionNoEncontradaExcepcion("No se pueden eliminar transacciones completadas");
        }
        
        this.transaccionRepositorio.delete(transaccion);
    }
} 