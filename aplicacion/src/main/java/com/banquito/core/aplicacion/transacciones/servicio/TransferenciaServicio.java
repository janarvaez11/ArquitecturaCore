package com.banquito.core.aplicacion.transacciones.servicio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.transacciones.excepcion.CuentaInvalidaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.MontoInvalidoExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.TransaccionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.TransferenciaInvalidaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.TransferenciaNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.modelo.Transferencia;
import com.banquito.core.aplicacion.transacciones.modelo.TransferenciaId;
import com.banquito.core.aplicacion.transacciones.modelo.Transaccion;
import com.banquito.core.aplicacion.transacciones.repositorio.TransferenciaRepositorio;

@Service
public class TransferenciaServicio {

    private final TransferenciaRepositorio transferenciaRepositorio;
    private final TransaccionServicio transaccionServicio;
    private final TipoTransaccionServicio tipoTransaccionServicio;

    private static final BigDecimal COMISION_TRANSFERENCIA_PROPIA = new BigDecimal("0.00");
    private static final BigDecimal COMISION_TRANSFERENCIA_TERCEROS = new BigDecimal("1.50");
    private static final BigDecimal MONTO_MINIMO_TRANSFERENCIA = new BigDecimal("1.00");
    private static final BigDecimal MONTO_MAXIMO_TRANSFERENCIA = new BigDecimal("50000.00");

    public TransferenciaServicio(TransferenciaRepositorio transferenciaRepositorio,
                                TransaccionServicio transaccionServicio,
                                TipoTransaccionServicio tipoTransaccionServicio) {
        this.transferenciaRepositorio = transferenciaRepositorio;
        this.transaccionServicio = transaccionServicio;
        this.tipoTransaccionServicio = tipoTransaccionServicio;
    }

    @Transactional(readOnly = true)
    public Transferencia findById(TransferenciaId id) {
        if (id == null) {
            throw new TransferenciaInvalidaExcepcion("El ID de la transferencia no puede ser nulo");
        }
        
        Optional<Transferencia> transferenciaOptional = this.transferenciaRepositorio.findById(id);
        if (transferenciaOptional.isPresent()) {
            return transferenciaOptional.get();
        } else {
            throw new TransferenciaNoEncontradaExcepcion("La transferencia con ID: " + id + " no existe");
        }
    }

    @Transactional(readOnly = true)
    public List<Transferencia> findAll() {
        List<Transferencia> transferencias = this.transferenciaRepositorio.findAll();
        if (transferencias.isEmpty()) {
            throw new TransferenciaNoEncontradaExcepcion("No existen transferencias registradas");
        }
        return transferencias;
    }

    @Transactional
    public Transferencia create(Transferencia transferencia) {
        if (transferencia == null) {
            throw new TransferenciaInvalidaExcepcion("La transferencia no puede ser nula");
        }
        
        this.validarTransferenciaParaCreacion(transferencia);
        
        this.transaccionServicio.findById(transferencia.getId().getTransaccionId());
        
        this.tipoTransaccionServicio.findById(transferencia.getId().getTipoTransaccionId());
        
        BigDecimal comision = this.calcularComision(BigDecimal.ZERO, 
            transferencia.getId().getCuentaOrigenId(), transferencia.getId().getCuentaDestinoId());
        transferencia.setComision(comision);
        
        transferencia.setEsInterbancaria(this.determinarSiEsInterbancaria(
            transferencia.getId().getCuentaOrigenId(), transferencia.getId().getCuentaDestinoId()));
        
        try {
            return this.transferenciaRepositorio.save(transferencia);
        } catch (Exception e) {
            throw new TransferenciaInvalidaExcepcion("Error al crear la transferencia: " + e.getMessage());
        }
    }

    @Transactional
    public Transferencia update(TransferenciaId id, Transferencia transferencia) {
        this.validarTransferenciaParaActualizacion(id, transferencia);
        
        Transferencia transferenciaExistente = this.findById(id);
        
        Transaccion transaccion = this.transaccionServicio.findById(transferenciaExistente.getId().getTransaccionId());
        
        this.tipoTransaccionServicio.findById(transaccion.getTipoTransaccionId());
        
        if (transferencia.getComision() != null) {
            transferenciaExistente.setComision(transferencia.getComision());
        }
        
        if (transferencia.getEsInterbancaria() != null) {
            transferenciaExistente.setEsInterbancaria(transferencia.getEsInterbancaria());
            BigDecimal nuevaComision = this.calcularComision(transaccion.getMonto(), 
                transferenciaExistente.getId().getCuentaOrigenId(), 
                transferenciaExistente.getId().getCuentaDestinoId());
            transferenciaExistente.setComision(nuevaComision);
        }

        return this.transferenciaRepositorio.save(transferenciaExistente);
    }

    @Transactional
    public Transferencia procesarTransferencia(Integer cuentaOrigenId, Integer cuentaDestinoId, 
                                              BigDecimal monto, String descripcion) {
        this.validarDatosTransferencia(cuentaOrigenId, cuentaDestinoId, monto, descripcion);
        
        Transaccion transaccion = new Transaccion();
        transaccion.setCuentaId(cuentaOrigenId);
        transaccion.setMonto(monto);
        transaccion.setTipoTransaccionId(1);
        transaccion.setDescripcion(descripcion);
        transaccion.setFechaTransaccion(new java.sql.Date(System.currentTimeMillis()));
        transaccion.setFechaContable(new java.sql.Date(System.currentTimeMillis()));
        transaccion.setCreadoEn(new java.sql.Timestamp(System.currentTimeMillis()));
        
        transaccion = this.transaccionServicio.create(transaccion);
        
        TransferenciaId transferenciaId = new TransferenciaId();
        transferenciaId.setTipoTransaccionId(transaccion.getTipoTransaccionId());
        transferenciaId.setTransferenciaId(1);
        transferenciaId.setTransaccionId(transaccion.getTransaccionId());
        transferenciaId.setCuentaOrigenId(cuentaOrigenId);
        transferenciaId.setCuentaDestinoId(cuentaDestinoId);
        
        Transferencia transferencia = new Transferencia();
        transferencia.setId(transferenciaId);
        transferencia.setComision(this.calcularComision(monto, cuentaOrigenId, cuentaDestinoId));
        transferencia.setEsInterbancaria(this.determinarSiEsInterbancaria(cuentaOrigenId, cuentaDestinoId));
        
        return this.transferenciaRepositorio.save(transferencia);
    }

    private void validarTransferenciaParaCreacion(Transferencia transferencia) {
        if (transferencia.getId() == null) {
            throw new TransferenciaInvalidaExcepcion("El ID de la transferencia es obligatorio");
        }
        
        TransferenciaId id = transferencia.getId();
        
        if (id.getCuentaOrigenId() == null || id.getCuentaOrigenId() <= 0) {
            throw new CuentaInvalidaExcepcion("La cuenta origen debe ser válida");
        }
        
        if (id.getCuentaDestinoId() == null || id.getCuentaDestinoId() <= 0) {
            throw new CuentaInvalidaExcepcion("La cuenta destino debe ser válida");
        }
        
        if (id.getCuentaOrigenId().equals(id.getCuentaDestinoId())) {
            throw new TransferenciaInvalidaExcepcion("La cuenta origen y destino no pueden ser la misma");
        }
        
        if (id.getTransaccionId() == null || id.getTransaccionId() <= 0) {
            throw new TransferenciaInvalidaExcepcion("El ID de la transacción debe ser válido");
        }
        
        if (id.getTipoTransaccionId() == null || id.getTipoTransaccionId() <= 0) {
            throw new TransferenciaInvalidaExcepcion("El ID del tipo de transacción debe ser válido");
        }
    }

    private void validarTransferenciaParaActualizacion(TransferenciaId id, Transferencia transferencia) {
        if (id == null) {
            throw new TransferenciaInvalidaExcepcion("El ID de la transferencia no puede ser nulo");
        }
        
        if (transferencia == null) {
            throw new TransferenciaInvalidaExcepcion("Los datos de la transferencia no pueden ser nulos");
        }
    }

    private void validarDatosTransferencia(Integer cuentaOrigenId, Integer cuentaDestinoId, 
                                         BigDecimal monto, String descripcion) {
        if (cuentaOrigenId == null || cuentaOrigenId <= 0) {
            throw new CuentaInvalidaExcepcion("La cuenta origen debe ser válida");
        }
        
        if (cuentaDestinoId == null || cuentaDestinoId <= 0) {
            throw new CuentaInvalidaExcepcion("La cuenta destino debe ser válida");
        }
        
        if (cuentaOrigenId.equals(cuentaDestinoId)) {
            throw new TransferenciaInvalidaExcepcion("La cuenta origen y destino no pueden ser la misma");
        }
        
        if (monto == null) {
            throw new MontoInvalidoExcepcion("El monto de la transferencia es obligatorio");
        }
        
        if (monto.compareTo(MONTO_MINIMO_TRANSFERENCIA) < 0) {
            throw new MontoInvalidoExcepcion("El monto mínimo para transferencias es: " + MONTO_MINIMO_TRANSFERENCIA);
        }
        
        if (monto.compareTo(MONTO_MAXIMO_TRANSFERENCIA) > 0) {
            throw new MontoInvalidoExcepcion("El monto máximo para transferencias es: " + MONTO_MAXIMO_TRANSFERENCIA);
        }
        
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new TransferenciaInvalidaExcepcion("La descripción de la transferencia es obligatoria");
        }
        
        if (descripcion.length() > 200) {
            throw new TransferenciaInvalidaExcepcion("La descripción no puede exceder 200 caracteres");
        }
    }

    private BigDecimal calcularComision(BigDecimal monto, Integer cuentaOrigenId, Integer cuentaDestinoId) {
        if (this.sonCuentasPropias(cuentaOrigenId, cuentaDestinoId)) {
            return COMISION_TRANSFERENCIA_PROPIA;
        } else {
            return COMISION_TRANSFERENCIA_TERCEROS;
        }
    }

    private boolean sonCuentasPropias(Integer cuentaOrigenId, Integer cuentaDestinoId) {
        return false;
    }

    private boolean determinarSiEsInterbancaria(Integer cuentaOrigenId, Integer cuentaDestinoId) {
        return !this.sonCuentasPropias(cuentaOrigenId, cuentaDestinoId);
    }

    @Transactional
    public void delete(TransferenciaId id) {
        Transferencia transferencia = this.findById(id);
        
        Transaccion transaccion = this.transaccionServicio.findById(transferencia.getId().getTransaccionId());
        if ("COM".equals(transaccion.getEstadoId())) {
            throw new TransferenciaInvalidaExcepcion("No se pueden eliminar transferencias completadas");
        }
        
        this.transferenciaRepositorio.delete(transferencia);
    }
} 