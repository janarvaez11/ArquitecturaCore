package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.excepcion.ActualizarExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.CrearExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.EliminarExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.NoEncontradoExcepcion;
import com.banquito.core.aplicacion.clientes.modelo.Persona;
import com.banquito.core.aplicacion.clientes.repositorio.PersonaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaServicio {

    private final PersonaRepositorio personaRepositorio;

    public PersonaServicio(PersonaRepositorio personaRepositorio) {
        this.personaRepositorio = personaRepositorio;
    }

    public List<Persona> buscarTodos() {
        return personaRepositorio.findAll();
    }

    public Persona buscarPorId(Integer id) {
        Optional<Persona> persona = personaRepositorio.findById(id);
        if (persona.isPresent()) {
            return persona.get();
        }else {
            throw new NoEncontradoExcepcion("El id:" + id + "No corresponde a niguna persona registrada", "Persona"  );
        }

    }

    @Transactional
    public void crear(Persona persona) {
        try {
            personaRepositorio.save(persona);
        }catch (RuntimeException rte) {
            throw new CrearExcepcion("Error al crear una persona. Texto: " + rte.getMessage(),"Persona");
        }
    }

    @Transactional
    public void actualizar(Persona persona) {
        try {
            Optional<Persona> personaOptional = this.personaRepositorio.findById(persona.getIdPersona());
            if (personaOptional.isPresent()) {
                Persona personaDB = personaOptional.get();

                if (persona.getTipoIdentificación() != null) {
                    personaDB.setTipoIdentificación(persona.getTipoIdentificación());
                }

                if (persona.getEstadoCivil() != null) {
                    personaDB.setEstadoCivil(persona.getEstadoCivil());
                }

                if (persona.getNivelEstudio() != null) {
                    personaDB.setNivelEstudio(persona.getNivelEstudio());
                }

                if (persona.getCorreoElectronico() != null) {
                    personaDB.setCorreoElectronico(persona.getCorreoElectronico());
                }

                if (persona.getEstado() != null) {
                    personaDB.setEstado(persona.getEstado());
                }

                personaDB.setFechaActualizacion(Date.from(Instant.now()));

                personaRepositorio.save(personaDB);
            } else {
                throw new ActualizarExcepcion(String.valueOf(persona.getIdPersona()), "Persona");
            }
        } catch (RuntimeException rte) {
            throw new ActualizarExcepcion("Error al actualizar la persona: " + rte.getMessage(), "Persona");
        }
    }


    @Transactional
    public void eliminar(Integer id) {
        try {
            Optional<Persona> personaOptional = this.personaRepositorio.findById(id);

            if (personaOptional.isPresent()) {
                this.personaRepositorio.deleteById(id);
            }else {
                throw new EliminarExcepcion(String.valueOf(id), "Persona");
            }
        }catch (RuntimeException rte) {
            throw new EliminarExcepcion("Error al eliminar la persona:" + rte.getMessage(), "Persona");

        }
    }
}
