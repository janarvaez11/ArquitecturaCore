package com.banquito.core.aplicacion.transacciones.servicio;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.transacciones.excepcion.TransaccionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.transacciones.modelo.Cheque;
import com.banquito.core.aplicacion.transacciones.repositorio.ChequeRepositorio;

@Service
public class ChequeServicio {

    private final ChequeRepositorio chequeRepositorio;
    private final TransaccionServicio transaccionServicio;

    public ChequeServicio(ChequeRepositorio chequeRepositorio,
                         TransaccionServicio transaccionServicio) {
        this.chequeRepositorio = chequeRepositorio;
        this.transaccionServicio = transaccionServicio;
    }

    public Cheque findById(Integer id) {
        if (id == null || id <= 0) {
            throw new TransaccionNoEncontradaExcepcion("El ID del cheque no puede ser nulo o menor a 1");
        }
        
        Optional<Cheque> chequeOptional = this.chequeRepositorio.findById(id);
        if (chequeOptional.isPresent()) {
            return chequeOptional.get();
        } else {
            throw new TransaccionNoEncontradaExcepcion("El cheque con ID: " + id + " no existe");
        }
    }

    public List<Cheque> findAll() {
        return this.chequeRepositorio.findAll();
    }

    @Transactional
    public Cheque create(Cheque cheque) {
        if (cheque == null) {
            throw new TransaccionNoEncontradaExcepcion("El cheque no puede ser nulo");
        }

        this.validarCheque(cheque);
        this.transaccionServicio.findById(cheque.getTransaccionId());

        return this.chequeRepositorio.save(cheque);
    }

    private void validarCheque(Cheque cheque) {
        if (cheque.getNumeroCheque() == null || cheque.getNumeroCheque().trim().isEmpty()) {
            throw new TransaccionNoEncontradaExcepcion("El número de cheque es obligatorio");
        }
        
        if (cheque.getNombreBeneficiario() == null || cheque.getNombreBeneficiario().trim().isEmpty()) {
            throw new TransaccionNoEncontradaExcepcion("El nombre del beneficiario es obligatorio");
        }
        
        if (cheque.getFechaEmision() == null) {
            throw new TransaccionNoEncontradaExcepcion("La fecha de emisión es obligatoria");
        }
        
        Date fechaActual = new Date(System.currentTimeMillis());
        if (cheque.getFechaEmision().after(fechaActual)) {
            throw new TransaccionNoEncontradaExcepcion("La fecha de emisión no puede ser futura");
        }
        
        if (cheque.getFechaCobro() != null && cheque.getFechaCobro().before(cheque.getFechaEmision())) {
            throw new TransaccionNoEncontradaExcepcion("La fecha de cobro no puede ser anterior a la fecha de emisión");
        }
    }

    @Transactional
    public void delete(Integer id) {
        Cheque cheque = this.findById(id);
        this.chequeRepositorio.delete(cheque);
    }
} 