package com.banquito.core.aplicacion.cuentas.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.ServicioAsociado;
import com.banquito.core.aplicacion.cuentas.repositorio.ServicioAsociadoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ServicioAsociadoServicio {

    private static final String ESTADO_ACTIVO = "ACTIVO";
    private static final String ESTADO_INACTIVO = "INACTIVO";

    private final ServicioAsociadoRepositorio servicioAsociadoRepositorio;

    public ServicioAsociadoServicio(ServicioAsociadoRepositorio servicioAsociadoRepositorio) {
        this.servicioAsociadoRepositorio = servicioAsociadoRepositorio;
    }

    public List<ServicioAsociado> listarTodos() {
        List<ServicioAsociado> servicios = this.servicioAsociadoRepositorio.findAll();
        if (servicios.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("ServicioAsociado", 
                "No existen servicios asociados registrados");
        }
        return servicios;
    }

    public Page<ServicioAsociado> listarTodosPaginado(Pageable pageable) {
        Page<ServicioAsociado> page = this.servicioAsociadoRepositorio.findAll(pageable);
        if (page.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("ServicioAsociado", 
                "No existen servicios asociados registrados");
        }
        return page;
    }

    public ServicioAsociado buscarPorId(Integer id) {
        return this.servicioAsociadoRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("ServicioAsociado", 
                    "No se encontró el servicio asociado con ID: " + id));
    }

    @Transactional
    public ServicioAsociado crear(ServicioAsociado servicioAsociado) {
        try {
            validarServicioAsociado(servicioAsociado);
            validarNombreUnico(servicioAsociado.getNombre(), null);
            servicioAsociado.setEstado(ESTADO_ACTIVO);
            return this.servicioAsociadoRepositorio.save(servicioAsociado);
        } catch (RuntimeException e) {
            throw new CrearEntidadExcepcion("ServicioAsociado", 
                "Error al crear el servicio asociado: " + e.getMessage());
        }
    }

    @Transactional
    public ServicioAsociado actualizar(Integer id, ServicioAsociado servicioAsociado) {
        try {
            ServicioAsociado servicioExistente = buscarPorId(id);
            validarServicioAsociado(servicioAsociado);
            validarNombreUnico(servicioAsociado.getNombre(), id);
            
            servicioExistente.setNombre(servicioAsociado.getNombre());
            servicioExistente.setDescripcion(servicioAsociado.getDescripcion());
            servicioExistente.setEstado(servicioAsociado.getEstado());
            
            return this.servicioAsociadoRepositorio.save(servicioExistente);
        } catch (RuntimeException e) {
            throw new ActualizarEntidadExcepcion("ServicioAsociado", 
                "Error al actualizar el servicio asociado: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        try {
            ServicioAsociado servicio = buscarPorId(id);
            if (ESTADO_ACTIVO.equals(servicio.getEstado())) {
                throw new EliminarEntidadExcepcion("ServicioAsociado", 
                    "No se puede eliminar un servicio asociado en estado activo");
            }
            this.servicioAsociadoRepositorio.delete(servicio);
        } catch (RuntimeException e) {
            throw new EliminarEntidadExcepcion("ServicioAsociado", 
                "Error al eliminar el servicio asociado: " + e.getMessage());
        }
    }

    public List<ServicioAsociado> buscarPorNombre(String nombre) {
        List<ServicioAsociado> servicios = this.servicioAsociadoRepositorio.findByNombreContaining(nombre);
        if (servicios.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("ServicioAsociado", 
                "No se encontraron servicios asociados con el nombre: " + nombre);
        }
        return servicios;
    }

    public List<ServicioAsociado> buscarPorEstado(String estado) {
        List<ServicioAsociado> servicios = this.servicioAsociadoRepositorio.findByEstado(estado);
        if (servicios.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("ServicioAsociado", 
                "No se encontraron servicios asociados en estado: " + estado);
        }
        return servicios;
    }

    public List<ServicioAsociado> buscarPorCuenta(Integer idCuenta) {
        List<ServicioAsociado> servicios = this.servicioAsociadoRepositorio
            .findByServicioTipoCuentas_Cuenta_IdCuenta(idCuenta);
        if (servicios.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("ServicioAsociado", 
                "No se encontraron servicios asociados para la cuenta: " + idCuenta);
        }
        return servicios;
    }

    private void validarServicioAsociado(ServicioAsociado servicioAsociado) {
        if (servicioAsociado.getNombre() == null || servicioAsociado.getNombre().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("ServicioAsociado", "El nombre es obligatorio");
        }
        if (servicioAsociado.getDescripcion() == null || servicioAsociado.getDescripcion().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("ServicioAsociado", "La descripción es obligatoria");
        }
        if (servicioAsociado.getEstado() == null || servicioAsociado.getEstado().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("ServicioAsociado", "El estado es obligatorio");
        }
        validarEstado(servicioAsociado.getEstado());
    }

    private void validarNombreUnico(String nombre, Integer idExcluir) {
        List<ServicioAsociado> serviciosExistentes = this.servicioAsociadoRepositorio.findAll();
        for (ServicioAsociado servicio : serviciosExistentes) {
            if (servicio.getNombre().equalsIgnoreCase(nombre) && 
                (idExcluir == null || !idExcluir.equals(servicio.getIdServicio()))) {
                throw new CrearEntidadExcepcion("ServicioAsociado", 
                    "Ya existe un servicio asociado con el nombre: " + nombre);
            }
        }
    }

    private void validarEstado(String estado) {
        if (!ESTADO_ACTIVO.equals(estado) && !ESTADO_INACTIVO.equals(estado)) {
            throw new CrearEntidadExcepcion("ServicioAsociado", 
                "El estado debe ser: " + ESTADO_ACTIVO + " o " + ESTADO_INACTIVO);
        }
    }
}
