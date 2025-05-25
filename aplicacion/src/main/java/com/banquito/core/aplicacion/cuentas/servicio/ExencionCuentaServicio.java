package com.banquito.core.aplicacion.cuentas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.ExencionCuentaNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.ExencionCuenta;
import com.banquito.core.aplicacion.cuentas.repositorio.ExencionCuentaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ExencionCuentaServicio {

    private final ExencionCuentaRepositorio exencionCuentaRepositorio;

    public ExencionCuentaServicio(ExencionCuentaRepositorio exencionCuentaRepositorio) {
        this.exencionCuentaRepositorio = exencionCuentaRepositorio;
    }

    public List<ExencionCuenta> findAll() {
        return this.exencionCuentaRepositorio.findAll();
    }

    public ExencionCuenta findById(Integer id) {
        Optional<ExencionCuenta> exencionCuentaOptional = this.exencionCuentaRepositorio.findById(id);
        if (exencionCuentaOptional.isPresent()) {
            return exencionCuentaOptional.get();
        } else {
            throw new ExencionCuentaNoEncontradaExcepcion("ExencionCuenta", "No se encontró la exención de cuenta con ID: " + id);
        }
    }

    @Transactional
    public void create(ExencionCuenta exencionCuenta) {
        try {
            this.exencionCuentaRepositorio.save(exencionCuenta);
        } catch (Exception e) {
            throw new CrearEntidadExcepcion("ExencionCuenta", "Error al crear la exención de cuenta. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void update(ExencionCuenta exencionCuenta) {
        try {
            Optional<ExencionCuenta> exencionCuentaOptional = this.exencionCuentaRepositorio.findById(exencionCuenta.getIdExencion());
            if (exencionCuentaOptional.isPresent()) {
                ExencionCuenta exencionCuentaDB = exencionCuentaOptional.get();
                exencionCuentaDB.setNombre(exencionCuenta.getNombre());
                exencionCuentaDB.setDescripcion(exencionCuenta.getDescripcion());
                this.exencionCuentaRepositorio.save(exencionCuentaDB);
            } else {
                throw new ExencionCuentaNoEncontradaExcepcion("ExencionCuenta", "No se encontró la exención de cuenta con ID: " + exencionCuenta.getIdExencion());
            }
        } catch (Exception e) {
            throw new ActualizarEntidadExcepcion("ExencionCuenta", "Error al actualizar la exención de cuenta. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<ExencionCuenta> exencionCuentaOptional = this.exencionCuentaRepositorio.findById(id);
            if (exencionCuentaOptional.isPresent()) {
                this.exencionCuentaRepositorio.delete(exencionCuentaOptional.get());
            } else {
                throw new ExencionCuentaNoEncontradaExcepcion("ExencionCuenta", "No se encontró la exención de cuenta con ID: " + id);
            }
        } catch (Exception e) {
            throw new EliminarEntidadExcepcion("ExencionCuenta", "Error al eliminar la exención de cuenta. Error: " + e.getMessage());
        }
    }
}
