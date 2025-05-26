package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.excepcion.ActualizarExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.CrearExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.EliminarExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.NoEncontradoExcepcion;
import com.banquito.core.aplicacion.clientes.modelo.Accionista;
import com.banquito.core.aplicacion.clientes.repositorio.AccionistaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccionistaServicio {

    private final AccionistaRepositorio accionistaRepositorio;

    public AccionistaServicio(AccionistaRepositorio accionistaRepositorio) {
        this.accionistaRepositorio = accionistaRepositorio;
    }

    public List<Accionista> buscarTodos() {
        return accionistaRepositorio.findAll();
    }

    public Accionista buscarPorId(Integer id) {
        Optional<Accionista> accionista = accionistaRepositorio.findById(id);
        if (accionista.isPresent()) {
            return accionista.get();
        } else {
            throw new NoEncontradoExcepcion("Error al buscar el accionista: " + id, "Accionista");
        }
    }


    @Transactional
    public void crear(Accionista accionista) {
        try {
            accionistaRepositorio.save(accionista);
        } catch (RuntimeException rte) {
            throw new CrearExcepcion("Error al crear el accionista: " + rte.getMessage(), "Accionista");
        }
    }

    @Transactional
    public void actualizar(Accionista accionista) {
        try {
            Optional<Accionista> accionistaOptional = this.accionistaRepositorio.findById(accionista.getIdAccionista());
            if (accionistaOptional.isPresent()) {
                Accionista accionistaDB = accionistaOptional.get();

                if (accionista.getEmpresa() != null) {
                    accionistaDB.setEmpresa(accionista.getEmpresa());
                }
                if (accionista.getParticipacion() != null) {
                    accionistaDB.setParticipacion(accionista.getParticipacion());
                }
                if (accionista.getTipoEntidad() != null) {
                    accionistaDB.setTipoEntidad(accionista.getTipoEntidad());
                }

                accionistaRepositorio.save(accionistaDB);
            } else {
                throw new ActualizarExcepcion(String.valueOf(accionista.getIdAccionista()), "Accionista");
            }
        } catch (RuntimeException rte) {
            throw new ActualizarExcepcion("Error al actualizar el accionista: " + rte.getMessage(), "Accionista");
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        try {
            Optional<Accionista> accionistaOptional = accionistaRepositorio.findById(id);

            if (accionistaOptional.isPresent()) {
                accionistaRepositorio.deleteById(id);
            } else {
                throw new EliminarExcepcion(String.valueOf(id), "Accionista");
            }
        } catch (RuntimeException rte) {
            throw new EliminarExcepcion("Error al eliminar el accionista: " + rte.getMessage(), "Accionista");
        }
    }
}
