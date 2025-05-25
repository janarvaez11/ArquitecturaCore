package com.banquito.core.aplicacion.cuentas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.TasaSaldoNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.TasaSaldo;
import com.banquito.core.aplicacion.cuentas.repositorio.TasaSaldoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class TasaSaldoServicio {

    private final TasaSaldoRepositorio tasaSaldoRepositorio;

    public TasaSaldoServicio(TasaSaldoRepositorio tasaSaldoRepositorio) {
        this.tasaSaldoRepositorio = tasaSaldoRepositorio;
    }

    public List<TasaSaldo> findAll() {
        return this.tasaSaldoRepositorio.findAll();
    }

    public TasaSaldo findById(Integer id) {
        Optional<TasaSaldo> tasaSaldoOptional = this.tasaSaldoRepositorio.findById(id);
        if (tasaSaldoOptional.isPresent()) {
            return tasaSaldoOptional.get();
        } else {
            throw new TasaSaldoNoEncontradaExcepcion("TasaSaldo", "No se encontró la tasa por saldo con ID: " + id);
        }
    }

    @Transactional
    public void create(TasaSaldo tasaSaldo) {
        try {
            this.tasaSaldoRepositorio.save(tasaSaldo);
        } catch (Exception e) {
            throw new CrearEntidadExcepcion("TasaSaldo", "Error al crear la tasa por saldo. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void update(TasaSaldo tasaSaldo) {
        try {
            Optional<TasaSaldo> tasaSaldoOptional = this.tasaSaldoRepositorio.findById(tasaSaldo.getIdSaldo());
            if (tasaSaldoOptional.isPresent()) {
                TasaSaldo tasaSaldoDB = tasaSaldoOptional.get();
                tasaSaldoDB.setSaldoMinimo(tasaSaldo.getSaldoMinimo());
                tasaSaldoDB.setSaldoMaximo(tasaSaldo.getSaldoMaximo());
                tasaSaldoDB.setTasa(tasaSaldo.getTasa());
                this.tasaSaldoRepositorio.save(tasaSaldoDB);
            } else {
                throw new TasaSaldoNoEncontradaExcepcion("TasaSaldo", "No se encontró la tasa por saldo con ID: " + tasaSaldo.getIdSaldo());
            }
        } catch (Exception e) {
            throw new ActualizarEntidadExcepcion("TasaSaldo", "Error al actualizar la tasa por saldo. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<TasaSaldo> tasaSaldoOptional = this.tasaSaldoRepositorio.findById(id);
            if (tasaSaldoOptional.isPresent()) {
                this.tasaSaldoRepositorio.delete(tasaSaldoOptional.get());
            } else {
                throw new TasaSaldoNoEncontradaExcepcion("TasaSaldo", "No se encontró la tasa por saldo con ID: " + id);
            }
        } catch (Exception e) {
            throw new EliminarEntidadExcepcion("TasaSaldo", "Error al eliminar la tasa por saldo. Error: " + e.getMessage());
        }
    }
}
