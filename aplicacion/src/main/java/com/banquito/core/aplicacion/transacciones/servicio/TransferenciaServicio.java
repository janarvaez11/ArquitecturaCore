package com.banquito.core.aplicacion.transacciones.servicio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.transacciones.excepcion.CuentaInvalidaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.MontoInvalidoExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.TransaccionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.modelo.Transferencia;
import com.banquito.core.aplicacion.transacciones.modelo.TransferenciaId;
import com.banquito.core.aplicacion.transacciones.repositorio.TransferenciaRepositorio;

@Service
public class TransferenciaServicio {

    private final TransferenciaRepositorio transferenciaRepositorio;
    private final TransaccionServicio transaccionServicio;
    private final TipoTransaccionServicio tipoTransaccionServicio;

    // Comisiones para transferencias
    private static final BigDecimal COMISION_INTERBANCARIA = new BigDecimal("2.50");
    private static final BigDecimal COMISION_MISMA_ENTIDAD = new BigDecimal("0.50");
    private static final BigDecimal MONTO_MINIMO_TRANSFERENCIA = new BigDecimal("1.00");
    private static final BigDecimal MONTO_MAXIMO_TRANSFERENCIA = new BigDecimal("50000.00");

    public TransferenciaServicio(TransferenciaRepositorio transferenciaRepositorio,
                                TransaccionServicio transaccionServicio,
                                TipoTransaccionServicio tipoTransaccionServicio) {
        this.transferenciaRepositorio = transferenciaRepositorio;
        this.transaccionServicio = transaccionServicio;
        this.tipoTransaccionServicio = tipoTransaccionServicio;
    }

    public Transferencia findById(TransferenciaId id) {
        if (id == null) {
            throw new TransaccionNoEncontradaExcepcion("El ID de la transferencia no puede ser nulo");
        }
        
        Optional<Transferencia> transferenciaOptional = this.transferenciaRepositorio.findById(id);
        if (transferenciaOptional.isPresent()) {
            return transferenciaOptional.get();
        } else {
            throw new TransaccionNoEncontradaExcepcion("La transferencia con ID: " + id + " no existe");
        }
    }

    public List<Transferencia> findAll() {
        List<Transferencia> transferencias = this.transferenciaRepositorio.findAll();
        if (transferencias.isEmpty()) {
            throw new TransaccionNoEncontradaExcepcion("No existen transferencias registradas");
        }
        return transferencias;
    }

    @Transactional
    public Transferencia create(Transferencia transferencia) {
        if (transferencia == null) {
            throw new TransaccionNoEncontradaExcepcion("La transferencia no puede ser nula");
        }

        // Validaciones de reglas de negocio específicas para transferencias
        this.validarTransferencia(transferencia);
        
        // Verificar que la transacción existe
        this.transaccionServicio.findById(transferencia.getId().getTransaccionId());
        
        // Verificar que el tipo de transacción existe
        this.tipoTransaccionServicio.findById(transferencia.getId().getTipoTransaccionId());

        // Calcular comisión automáticamente
        BigDecimal comision = this.calcularComision(transferencia);
        transferencia.setComision(comision);

        return this.transferenciaRepositorio.save(transferencia);
    }

    @Transactional
    public Transferencia update(TransferenciaId id, Transferencia transferencia) {
        Transferencia transferenciaExistente = this.findById(id);
        
        // Solo permitir actualizar ciertos campos
        if (transferencia.getEsInterbancaria() != null) {
            transferenciaExistente.setEsInterbancaria(transferencia.getEsInterbancaria());
            // Recalcular comisión si cambia el tipo
            BigDecimal nuevaComision = this.calcularComision(transferenciaExistente);
            transferenciaExistente.setComision(nuevaComision);
        }

        return this.transferenciaRepositorio.save(transferenciaExistente);
    }

    @Transactional
    public Transferencia procesarTransferencia(Integer cuentaOrigenId, Integer cuentaDestinoId, 
                                              BigDecimal monto, String descripcion, Boolean esInterbancaria) {
        
        // Validar datos de entrada
        this.validarDatosTransferencia(cuentaOrigenId, cuentaDestinoId, monto, descripcion);
        
        // Crear la transacción principal primero
        com.banquito.core.aplicacion.transacciones.modelo.Transaccion transaccion = 
            new com.banquito.core.aplicacion.transacciones.modelo.Transaccion();
        transaccion.setTipoTransaccionId(1); // Asumir que 1 es transferencia
        transaccion.setCuentaId(cuentaOrigenId);
        transaccion.setMonto(monto);
        transaccion.setDescripcion(descripcion);
        
        transaccion = this.transaccionServicio.create(transaccion);
        
        // Crear la transferencia
        TransferenciaId transferenciaId = new TransferenciaId();
        transferenciaId.setTransferenciaId(1); // Se podría generar automáticamente
        transferenciaId.setTipoTransaccionId(transaccion.getTipoTransaccionId());
        transferenciaId.setTransaccionId(transaccion.getTransaccionId());
        transferenciaId.setCuentaOrigenId(cuentaOrigenId);
        transferenciaId.setCuentaDestinoId(cuentaDestinoId);
        
        Transferencia transferencia = new Transferencia();
        transferencia.setId(transferenciaId);
        transferencia.setEsInterbancaria(esInterbancaria != null ? esInterbancaria : false);
        
        return this.create(transferencia);
    }

    // Métodos de validación privados
    private void validarTransferencia(Transferencia transferencia) {
        if (transferencia.getId() == null) {
            throw new TransaccionNoEncontradaExcepcion("El ID de la transferencia es obligatorio");
        }

        TransferenciaId id = transferencia.getId();
        
        if (id.getCuentaOrigenId() == null || id.getCuentaOrigenId() <= 0) {
            throw new CuentaInvalidaExcepcion("La cuenta de origen debe ser válida");
        }
        
        if (id.getCuentaDestinoId() == null || id.getCuentaDestinoId() <= 0) {
            throw new CuentaInvalidaExcepcion("La cuenta de destino debe ser válida");
        }
        
        if (id.getCuentaOrigenId().equals(id.getCuentaDestinoId())) {
            throw new CuentaInvalidaExcepcion("La cuenta de origen no puede ser igual a la cuenta de destino");
        }
        
        if (transferencia.getEsInterbancaria() == null) {
            throw new TransaccionNoEncontradaExcepcion("Debe especificar si es transferencia interbancaria");
        }
    }

    private void validarDatosTransferencia(Integer cuentaOrigenId, Integer cuentaDestinoId, 
                                          BigDecimal monto, String descripcion) {
        if (cuentaOrigenId == null || cuentaOrigenId <= 0) {
            throw new CuentaInvalidaExcepcion("La cuenta de origen debe ser válida");
        }
        
        if (cuentaDestinoId == null || cuentaDestinoId <= 0) {
            throw new CuentaInvalidaExcepcion("La cuenta de destino debe ser válida");
        }
        
        if (cuentaOrigenId.equals(cuentaDestinoId)) {
            throw new CuentaInvalidaExcepcion("La cuenta de origen no puede ser igual a la cuenta de destino");
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
            throw new TransaccionNoEncontradaExcepcion("La descripción de la transferencia es obligatoria");
        }
    }

    private BigDecimal calcularComision(Transferencia transferencia) {
        if (transferencia.getEsInterbancaria()) {
            return COMISION_INTERBANCARIA;
        } else {
            return COMISION_MISMA_ENTIDAD;
        }
    }

    @Transactional
    public void delete(TransferenciaId id) {
        Transferencia transferencia = this.findById(id);
        
        // Verificar que la transacción asociada se pueda eliminar
        this.transaccionServicio.findById(transferencia.getId().getTransaccionId());
        
        this.transferenciaRepositorio.delete(transferencia);
    }
} 