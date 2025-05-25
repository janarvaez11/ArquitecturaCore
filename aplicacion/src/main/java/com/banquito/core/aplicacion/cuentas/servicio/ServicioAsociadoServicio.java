package com.banquito.core.aplicacion.cuentas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.ServicioAsociadoNoEncontradoExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.ServicioAsociado;
import com.banquito.core.aplicacion.cuentas.repositorio.ServicioAsociadoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ServicioAsociadoServicio {

    private final ServicioAsociadoRepositorio servicioAsociadoRepositorio;

    public ServicioAsociadoServicio(ServicioAsociadoRepositorio servicioAsociadoRepositorio) {
        this.servicioAsociadoRepositorio = servicioAsociadoRepositorio;
    }

    public List<ServicioAsociado> findAll() {
        return this.servicioAsociadoRepositorio.findAll();
    }

    public ServicioAsociado findById(Integer id) {
        Optional<ServicioAsociado> servicioAsociadoOptional = this.servicioAsociadoRepositorio.findById(id);
        if (servicioAsociadoOptional.isPresent()) {
            return servicioAsociadoOptional.get();
        } else {
            throw new ServicioAsociadoNoEncontradoExcepcion("ServicioAsociado", "No se encontró el servicio asociado con ID: " + id);
        }
    }

    @Transactional
    public void create(ServicioAsociado servicioAsociado) {
        try {
            this.servicioAsociadoRepositorio.save(servicioAsociado);
        } catch (Exception e) {
            throw new CrearEntidadExcepcion("ServicioAsociado", "Error al crear el servicio asociado. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void update(ServicioAsociado servicioAsociado) {
        try {
            Optional<ServicioAsociado> servicioAsociadoOptional = this.servicioAsociadoRepositorio.findById(servicioAsociado.getIdServicio());
            if (servicioAsociadoOptional.isPresent()) {
                ServicioAsociado servicioAsociadoDB = servicioAsociadoOptional.get();
                servicioAsociadoDB.setNombre(servicioAsociado.getNombre());
                servicioAsociadoDB.setDescripcion(servicioAsociado.getDescripcion());
                servicioAsociadoDB.setEstado(servicioAsociado.getEstado());
                this.servicioAsociadoRepositorio.save(servicioAsociadoDB);
            } else {
                throw new ServicioAsociadoNoEncontradoExcepcion("ServicioAsociado", "No se encontró el servicio asociado con ID: " + servicioAsociado.getIdServicio());
            }
        } catch (Exception e) {
            throw new ActualizarEntidadExcepcion("ServicioAsociado", "Error al actualizar el servicio asociado. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<ServicioAsociado> servicioAsociadoOptional = this.servicioAsociadoRepositorio.findById(id);
            if (servicioAsociadoOptional.isPresent()) {
                this.servicioAsociadoRepositorio.delete(servicioAsociadoOptional.get());
            } else {
                throw new ServicioAsociadoNoEncontradoExcepcion("ServicioAsociado", "No se encontró el servicio asociado con ID: " + id);
            }
        } catch (Exception e) {
            throw new EliminarEntidadExcepcion("ServicioAsociado", "Error al eliminar el servicio asociado. Error: " + e.getMessage());
        }
    }
}
