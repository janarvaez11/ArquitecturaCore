package com.banquito.core.aplicacion.transacciones.servicio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.transacciones.excepcion.ComisionInvalidaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.ComisionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.MontoInvalidoExcepcion;
import com.banquito.core.aplicacion.transacciones.modelo.ComisionTransaccion;
import com.banquito.core.aplicacion.transacciones.repositorio.ComisionTransaccionRepositorio;

@Service
public class ComisionTransaccionServicio {

    private final ComisionTransaccionRepositorio comisionRepositorio;

    private static final BigDecimal MONTO_MINIMO_COMISION = new BigDecimal("0.00");
    private static final BigDecimal MONTO_MAXIMO_COMISION = new BigDecimal("1000.00");
    
    private static final String TIPO_FIJO = "FIJO";
    private static final String TIPO_PORCENTUAL = "PORCENTUAL";
    private static final String TIPO_MIXTO = "MIXTO";

    public ComisionTransaccionServicio(ComisionTransaccionRepositorio comisionRepositorio) {
        this.comisionRepositorio = comisionRepositorio;
    }

    @Transactional(readOnly = true)
    public ComisionTransaccion findById(Integer id) {
        this.validarIdComision(id);
        
        Optional<ComisionTransaccion> comisionOptional = this.comisionRepositorio.findById(id);
        if (comisionOptional.isPresent()) {
            return comisionOptional.get();
        } else {
            throw new ComisionNoEncontradaExcepcion("La comisión con ID: " + id + " no existe");
        }
    }

    @Transactional(readOnly = true)
    public List<ComisionTransaccion> findAll() {
        List<ComisionTransaccion> comisiones = this.comisionRepositorio.findAll();
        if (comisiones.isEmpty()) {
            throw new ComisionNoEncontradaExcepcion("No existen comisiones registradas");
        }
        return comisiones;
    }

    @Transactional(readOnly = true)
    public List<ComisionTransaccion> findByTipoComision(String tipoComision) {
        if (tipoComision == null || tipoComision.trim().isEmpty()) {
            throw new ComisionInvalidaExcepcion("El tipo de comisión no puede ser nulo o vacío");
        }
        
        this.validarTipoComision(tipoComision);
        
        List<ComisionTransaccion> comisiones = this.comisionRepositorio.findByTipoComisionIgnoreCase(tipoComision.trim());
        if (comisiones.isEmpty()) {
            throw new ComisionNoEncontradaExcepcion("No se encontraron comisiones del tipo: " + tipoComision);
        }
        return comisiones;
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularComision(BigDecimal montoTransaccion, Integer comisionId) {
        if (montoTransaccion == null || montoTransaccion.compareTo(BigDecimal.ZERO) <= 0) {
            throw new MontoInvalidoExcepcion("El monto de la transacción debe ser mayor a 0 para calcular la comisión");
        }
        
        ComisionTransaccion comision = this.findById(comisionId);
        
        switch (comision.getTipoComision().toUpperCase()) {
            case TIPO_FIJO:
                return comision.getMonto();
                
            case TIPO_PORCENTUAL:
                return montoTransaccion.multiply(comision.getMonto())
                    .divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
                    
            case TIPO_MIXTO:
                BigDecimal montoFijo = new BigDecimal("5.00");
                BigDecimal comisionPorcentual = montoTransaccion.multiply(comision.getMonto())
                    .divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
                return montoFijo.add(comisionPorcentual);
                
            default:
                throw new ComisionInvalidaExcepcion("Tipo de comisión no válido: " + comision.getTipoComision());
        }
    }

    @Transactional
    public ComisionTransaccion create(ComisionTransaccion comision) {
        this.validarComisionParaCreacion(comision);
        
        if (comision.getTipoTransaccionId() != null) {
            this.validarTipoTransaccionId(comision.getTipoTransaccionId());
        }
        
        try {
            return this.comisionRepositorio.save(comision);
        } catch (Exception e) {
            throw new ComisionInvalidaExcepcion("Error al crear la comisión: " + e.getMessage());
        }
    }

    @Transactional
    public ComisionTransaccion update(Integer id, ComisionTransaccion comision) {
        this.validarIdComision(id);
        ComisionTransaccion comisionExistente = this.findById(id);
        
        if (comision == null) {
            throw new ComisionInvalidaExcepcion("Los datos de la comisión no pueden ser nulos");
        }
        
        if (comision.getTipoComision() != null && !comision.getTipoComision().trim().isEmpty()) {
            String nuevoTipo = comision.getTipoComision().trim().toUpperCase();
            this.validarTipoComision(nuevoTipo);
            comisionExistente.setTipoComision(nuevoTipo);
        }
        
        if (comision.getMonto() != null) {
            this.validarMonto(comision.getMonto());
            comisionExistente.setMonto(comision.getMonto());
        }
        
        if (comision.getTipoTransaccionId() != null) {
            this.validarTipoTransaccionId(comision.getTipoTransaccionId());
            comisionExistente.setTipoTransaccionId(comision.getTipoTransaccionId());
        }

        try {
            return this.comisionRepositorio.save(comisionExistente);
        } catch (Exception e) {
            throw new ComisionInvalidaExcepcion("Error al actualizar la comisión: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        this.validarIdComision(id);
        ComisionTransaccion comision = this.findById(id);
        
        this.validarPuedeBorrarse(id);
        
        try {
            this.comisionRepositorio.delete(comision);
        } catch (Exception e) {
            throw new ComisionInvalidaExcepcion("Error al eliminar la comisión: " + e.getMessage());
        }
    }

    private void validarIdComision(Integer id) {
        if (id == null || id <= 0) {
            throw new ComisionInvalidaExcepcion("El ID de la comisión debe ser un número entero positivo mayor a 0");
        }
    }

    private void validarComisionParaCreacion(ComisionTransaccion comision) {
        if (comision == null) {
            throw new ComisionInvalidaExcepcion("La comisión no puede ser nula");
        }

        if (comision.getTipoComision() == null || comision.getTipoComision().trim().isEmpty()) {
            throw new ComisionInvalidaExcepcion("El tipo de comisión es obligatorio");
        }
        this.validarTipoComision(comision.getTipoComision().trim().toUpperCase());
        
        if (comision.getMonto() == null) {
            throw new ComisionInvalidaExcepcion("El monto de la comisión es obligatorio");
        }
        this.validarMonto(comision.getMonto());

        this.validarCoherenciaTipoComision(comision);
    }

    private void validarTipoComision(String tipoComision) {
        if (!TIPO_FIJO.equals(tipoComision) && 
            !TIPO_PORCENTUAL.equals(tipoComision) && 
            !TIPO_MIXTO.equals(tipoComision)) {
            throw new ComisionInvalidaExcepcion("El tipo de comisión debe ser: FIJO, PORCENTUAL o MIXTO");
        }
    }

    private void validarMonto(BigDecimal monto) {
        if (monto.compareTo(MONTO_MINIMO_COMISION) < 0) {
            throw new MontoInvalidoExcepcion("El monto de la comisión no puede ser negativo");
        }
        
        if (monto.compareTo(MONTO_MAXIMO_COMISION) > 0) {
            throw new MontoInvalidoExcepcion("El monto de la comisión no puede exceder: " + MONTO_MAXIMO_COMISION);
        }
    }

    private void validarCoherenciaTipoComision(ComisionTransaccion comision) {
        String tipo = comision.getTipoComision().toUpperCase();
        
        if (TIPO_FIJO.equals(tipo)) {
            if (comision.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ComisionInvalidaExcepcion("Para comisión FIJA, el monto debe ser mayor a 0");
            }
        } else if (TIPO_PORCENTUAL.equals(tipo)) {
            if (comision.getMonto().compareTo(BigDecimal.ZERO) <= 0 || 
                comision.getMonto().compareTo(new BigDecimal("100")) > 0) {
                throw new ComisionInvalidaExcepcion("Para comisión PORCENTUAL, el monto debe estar entre 0.01 y 100");
            }
        } else if (TIPO_MIXTO.equals(tipo)) {
            if (comision.getMonto().compareTo(BigDecimal.ZERO) <= 0 || 
                comision.getMonto().compareTo(new BigDecimal("50")) > 0) {
                throw new ComisionInvalidaExcepcion("Para comisión MIXTA, el porcentaje (monto) debe estar entre 0.01 y 50");
            }
        }
    }

    private void validarTipoTransaccionId(Integer tipoTransaccionId) {
        if (tipoTransaccionId <= 0) {
            throw new ComisionInvalidaExcepcion("El ID del tipo de transacción debe ser un número positivo");
        }
    }

    private void validarPuedeBorrarse(Integer id) {
        boolean tieneTiposTransaccionAsociados = this.comisionRepositorio.existsTiposTransaccionByComisionId(id);
        if (tieneTiposTransaccionAsociados) {
            throw new ComisionInvalidaExcepcion("No se puede eliminar la comisión porque tiene tipos de transacción asociados");
        }
    }
} 