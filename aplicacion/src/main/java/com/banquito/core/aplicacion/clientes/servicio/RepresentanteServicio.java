package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.excepcion.ActualizarExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.CrearExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.EliminarExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.NoEncontradoExcepcion;
import com.banquito.core.aplicacion.clientes.modelo.Representante;
import com.banquito.core.aplicacion.clientes.modelo.RepresentanteId;
import com.banquito.core.aplicacion.clientes.repositorio.RepresentanteRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class RepresentanteServicio {

    private final RepresentanteRepositorio representanteRepositorio;

    public RepresentanteServicio(RepresentanteRepositorio representanteRepositorio) {
        this.representanteRepositorio = representanteRepositorio;
    }

    public List<Representante> buscarTodos() {
        return representanteRepositorio.findAll();
    }


    public Representante buscarPorId(RepresentanteId id) {
        if (id == null || id.getIdEmpresa() == null || id.getIdCliente() == null || id.getRol() == null || id.getRol().isBlank()) {
            throw new IllegalArgumentException("El ID del representante es inválido o incompleto.");
        }

        return representanteRepositorio.findById(id).orElseThrow(() ->
                new NoEncontradoExcepcion("No se encontró el representante con ID: " + id, "Representante"));
    }

    @Transactional
    public void crear(Representante representante) {
        try {
            validarRepresentanteParaCreacion(representante);

            if (representanteRepositorio.existsById(representante.getId())) {
                throw new CrearExcepcion("El representante ya existe con ID: " + representante.getId(), "Representante");
            }

            representante.setFechaAsignacion(Date.from(Instant.now()));
            representanteRepositorio.save(representante);

        } catch (RuntimeException e) {
            throw new CrearExcepcion("Error al crear el representante: " + e.getMessage(), "Representante");
        }
    }

    @Transactional
    public void actualizar(Representante representante) {
        try {
            validarRepresentanteParaActualizacion(representante);

            Representante representanteDB = representanteRepositorio.findById(representante.getId()).orElseThrow(() ->
                    new ActualizarExcepcion("No se encontró el representante con ID: " + representante.getId(), "Representante"));

            if (representante.getEstado() != null && !representante.getEstado().isBlank()) {
                representanteDB.setEstado(representante.getEstado());
                representanteDB.setFechaAsignacion(Date.from(Instant.now()));
            }

            representanteRepositorio.save(representanteDB);

        } catch (RuntimeException e) {
            throw new ActualizarExcepcion("Error al actualizar el representante: " + e.getMessage(), "Representante");
        }
    }

    @Transactional
    public void eliminar(RepresentanteId id) {
        try {
            if (id == null || id.getIdEmpresa() == null || id.getIdCliente() == null || id.getRol() == null || id.getRol().isBlank()) {
                throw new IllegalArgumentException("ID inválido para eliminar representante.");
            }

            if (!representanteRepositorio.existsById(id)) {
                throw new EliminarExcepcion("No se encontró el representante con ID: " + id, "Representante");
            }

            representanteRepositorio.deleteById(id);
        } catch (RuntimeException e) {
            throw new EliminarExcepcion("Error al eliminar el representante: " + e.getMessage(), "Representante");
        }
    }

    private void validarRepresentanteParaCreacion(Representante representante) {
        if (representante == null) {
            throw new IllegalArgumentException("El representante no puede ser null.");
        }
        if (representante.getId() == null ||
                representante.getId().getIdEmpresa() == null ||
                representante.getId().getIdCliente() == null ||
                representante.getId().getRol() == null || representante.getId().getRol().isBlank()) {
            throw new IllegalArgumentException("ID de representante incompleto o inválido.");
        }

        if (representante.getEmpresa() == null || representante.getCliente() == null) {
            throw new IllegalArgumentException("La empresa y el cliente son obligatorios.");
        }

        if (!estadoValido(representante.getEstado())) {
            throw new IllegalArgumentException("El estado del representante debe ser ACTIVO o INACTIVO.");
        }
    }

    private void validarRepresentanteParaActualizacion(Representante representante) {
        if (representante == null || representante.getId() == null) {
            throw new IllegalArgumentException("El representante o su ID no pueden ser null.");
        }

        if (representante.getEstado() != null && !estadoValido(representante.getEstado())) {
            throw new IllegalArgumentException("El estado del representante debe ser ACTIVO o INACTIVO.");
        }
    }

    private boolean estadoValido(String estado) {
        return "ACTIVO".equalsIgnoreCase(estado) || "INACTIVO".equalsIgnoreCase(estado);
    }
}
