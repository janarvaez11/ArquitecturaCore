package com.banquito.core.aplicacion.transacciones.servicio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.transacciones.excepcion.MontoInvalidoExcepcion;
import com.banquito.core.aplicacion.transacciones.excepcion.TransaccionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.modelo.ComisionTransaccion;
import com.banquito.core.aplicacion.transacciones.repositorio.ComisionTransaccionRepositorio;

@Service
public class ComisionTransaccionServicio {

    private final ComisionTransaccionRepositorio comisionRepositorio;
    private final TipoTransaccionServicio tipoTransaccionServicio;

    public ComisionTransaccionServicio(ComisionTransaccionRepositorio comisionRepositorio,
                                      TipoTransaccionServicio tipoTransaccionServicio) {
        this.comisionRepositorio = comisionRepositorio;
        this.tipoTransaccionServicio = tipoTransaccionServicio;
    }

    public ComisionTransaccion findById(Integer id) {
        if (id == null || id <= 0) {
            throw new TransaccionNoEncontradaExcepcion("El ID de la comisión no puede ser nulo o menor a 1");
        }
        
        Optional<ComisionTransaccion> comisionOptional = this.comisionRepositorio.findById(id);
        if (comisionOptional.isPresent()) {
            return comisionOptional.get();
        } else {
            throw new TransaccionNoEncontradaExcepcion("La comisión con ID: " + id + " no existe");
        }
    }

    public List<ComisionTransaccion> findAll() {
        return this.comisionRepositorio.findAll();
    }

    @Transactional
    public ComisionTransaccion create(ComisionTransaccion comision) {
        if (comision == null) {
            throw new TransaccionNoEncontradaExcepcion("La comisión no puede ser nula");
        }

        this.validarComision(comision);
        this.tipoTransaccionServicio.findById(comision.getTipoTransaccionId());

        return this.comisionRepositorio.save(comision);
    }

    private void validarComision(ComisionTransaccion comision) {
        if (comision.getTipoComision() == null || comision.getTipoComision().trim().isEmpty()) {
            throw new TransaccionNoEncontradaExcepcion("El tipo de comisión es obligatorio");
        }
        
        if (comision.getMonto() == null) {
            throw new MontoInvalidoExcepcion("El monto de la comisión es obligatorio");
        }
        
        if (comision.getMonto().compareTo(BigDecimal.ZERO) < 0) {
            throw new MontoInvalidoExcepcion("El monto de la comisión no puede ser negativo");
        }
    }

    @Transactional
    public void delete(Integer id) {
        ComisionTransaccion comision = this.findById(id);
        this.comisionRepositorio.delete(comision);
    }
} 