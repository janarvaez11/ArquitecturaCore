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
import java.util.Optional;

@Service
public class RepresentanteServicio {

    private final RepresentanteRepositorio representanteRepositorio;

    public RepresentanteServicio(RepresentanteRepositorio representanteRepositorio) {
        this.representanteRepositorio = representanteRepositorio;
    }

    public List<Representante> buscarTodos() {
        return representanteRepositorio.findAll();
    }

    public List<Representante> obtenerPorIdEmpresa(Integer idEmpresa) {

        if (idEmpresa == null) {
            throw new IllegalArgumentException("Id de empresa no puede ser nulo");
        }

        List<Representante> representantes = this.representanteRepositorio.findById_IdEmpresa(idEmpresa);

        if (representantes.isEmpty()) {
            throw new NoEncontradoExcepcion(idEmpresa.toString(), "Representantes");
        }

        return representantes;
    }



    public Representante buscarPorId(RepresentanteId id) {
        Optional<Representante> representante = representanteRepositorio.findById(id);
        if (representante.isPresent()) {
            return representante.get();
        } else {
            throw new NoEncontradoExcepcion("Error al buscar el representante: " + id, "Representante");
        }
    }

    @Transactional
    public void crear(Representante representante) {
        try {
            representante.setFechaAsignacion(Date.from(Instant.now()));
            representanteRepositorio.save(representante);
        } catch (RuntimeException e) {
            throw new CrearExcepcion("Error al crear el representante: " + e.getMessage(), "Representante");
        }
    }

    @Transactional
    public void actualizar(Representante representante) {
        try {
            Optional<Representante> representanteOptional = representanteRepositorio.findById(representante.getId());
            if (representanteOptional.isPresent()) {
                Representante representanteDB = representanteOptional.get();

                if (representante.getEstado() != null) {
                    representanteDB.setEstado(representante.getEstado());
                    representanteDB.setFechaAsignacion(Date.from(Instant.now()));
                }

                representanteRepositorio.save(representanteDB);
            } else {
                throw new ActualizarExcepcion(representante.getId().toString(), "Representante");
            }
        } catch (RuntimeException e) {
            throw new ActualizarExcepcion("Error al actualizar el representante: " + e.getMessage(), "Representante");
        }
    }


    @Transactional
    public void eliminar(RepresentanteId id) {
        try {
            Optional<Representante> representanteOptional = representanteRepositorio.findById(id);
            if (representanteOptional.isPresent()) {
                representanteRepositorio.deleteById(id);
            } else {
                throw new EliminarExcepcion(id.toString(), "Representante");
            }
        } catch (RuntimeException e) {
            throw new EliminarExcepcion("Error al eliminar el representante: " + e.getMessage(), "Representante");
        }
    }
}
