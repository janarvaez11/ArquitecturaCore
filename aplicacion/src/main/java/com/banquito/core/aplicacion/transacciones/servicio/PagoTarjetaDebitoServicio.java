package com.banquito.core.aplicacion.transacciones.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.transacciones.excepcion.TransaccionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.modelo.PagoTarjetaDebito;
import com.banquito.core.aplicacion.transacciones.repositorio.PagoTarjetaDebitoRepositorio;

@Service
public class PagoTarjetaDebitoServicio {

    private final PagoTarjetaDebitoRepositorio pagoRepositorio;
    private final TransaccionServicio transaccionServicio;

    public PagoTarjetaDebitoServicio(PagoTarjetaDebitoRepositorio pagoRepositorio,
                                    TransaccionServicio transaccionServicio) {
        this.pagoRepositorio = pagoRepositorio;
        this.transaccionServicio = transaccionServicio;
    }

    public PagoTarjetaDebito findById(Integer id) {
        if (id == null || id <= 0) {
            throw new TransaccionNoEncontradaExcepcion("El ID del pago no puede ser nulo o menor a 1");
        }
        
        Optional<PagoTarjetaDebito> pagoOptional = this.pagoRepositorio.findById(id);
        if (pagoOptional.isPresent()) {
            return pagoOptional.get();
        } else {
            throw new TransaccionNoEncontradaExcepcion("El pago con ID: " + id + " no existe");
        }
    }

    public List<PagoTarjetaDebito> findAll() {
        return this.pagoRepositorio.findAll();
    }

    @Transactional
    public PagoTarjetaDebito create(PagoTarjetaDebito pago) {
        if (pago == null) {
            throw new TransaccionNoEncontradaExcepcion("El pago no puede ser nulo");
        }

        this.validarPago(pago);
        this.transaccionServicio.findById(pago.getTransaccionId());

        return this.pagoRepositorio.save(pago);
    }

    private void validarPago(PagoTarjetaDebito pago) {
        if (pago.getNumeroTarjeta() == null || pago.getNumeroTarjeta().trim().isEmpty()) {
            throw new TransaccionNoEncontradaExcepcion("El número de tarjeta es obligatorio");
        }
        
        if (pago.getNumeroTarjeta().length() != 16) {
            throw new TransaccionNoEncontradaExcepcion("El número de tarjeta debe tener 16 dígitos");
        }
        
        if (!pago.getNumeroTarjeta().matches("\\d+")) {
            throw new TransaccionNoEncontradaExcepcion("El número de tarjeta solo debe contener dígitos");
        }
        
        if (pago.getNombreComercio() == null || pago.getNombreComercio().trim().isEmpty()) {
            throw new TransaccionNoEncontradaExcepcion("El nombre del comercio es obligatorio");
        }
        
        if (pago.getCodigoCategoriaComercio() == null || pago.getCodigoCategoriaComercio().trim().isEmpty()) {
            throw new TransaccionNoEncontradaExcepcion("El código de categoría del comercio es obligatorio");
        }
    }

    @Transactional
    public void delete(Integer id) {
        PagoTarjetaDebito pago = this.findById(id);
        this.pagoRepositorio.delete(pago);
    }
} 