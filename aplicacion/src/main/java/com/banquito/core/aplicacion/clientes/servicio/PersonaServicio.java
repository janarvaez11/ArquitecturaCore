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

    public Persona buscarPorNumeroIdentificacion(String numeroIdentificacion) {
        return personaRepositorio.findByNumeroIdentificacion(numeroIdentificacion)
                .orElseThrow(() -> new NoEncontradoExcepcion("Persona", "No se encontró persona con número de identificación: " + numeroIdentificacion));
    }

    @Transactional
    public void crear(Persona persona) {
        try {
            validarDatosObligatorios(persona);
            validarCorreo(persona.getCorreoElectronico());
            validarTipoIdentificacion(persona.getTipoIdentificacion());
            validarNumeroIdentificacionUnico(persona.getNumeroIdentificacion());
            validarLongitudNumeroIdentificacion(persona.getNumeroIdentificacion());
            validarFechaNacimiento(persona.getFechaNacimiento());

            if (persona.getFechaRegistro() == null) {
                persona.setFechaRegistro(Date.from(Instant.now()));
            }

            persona.setFechaActualizacion(Date.from(Instant.now()));

            personaRepositorio.save(persona);
        } catch (RuntimeException rte) {
            throw new CrearExcepcion("Error al crear una persona. Texto: " + rte.getMessage(), "Persona");
        }
    }

    @Transactional
    public void actualizar(Persona persona) {
        try {
            Optional<Persona> personaOptional = this.personaRepositorio.findById(persona.getIdPersona());
            if (personaOptional.isPresent()) {
                Persona personaDB = personaOptional.get();

                if (persona.getTipoIdentificacion() != null) {
                    validarTipoIdentificacion(persona.getTipoIdentificacion());
                    personaDB.setTipoIdentificacion(persona.getTipoIdentificacion());
                }

                if (persona.getEstadoCivil() != null) {
                    personaDB.setEstadoCivil(persona.getEstadoCivil());
                }

                if (persona.getNivelEstudio() != null) {
                    personaDB.setNivelEstudio(persona.getNivelEstudio());
                }

                if (persona.getCorreoElectronico() != null) {
                    validarCorreo(persona.getCorreoElectronico());
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
            } else {
                throw new EliminarExcepcion(String.valueOf(id), "Persona");
            }
        } catch (RuntimeException rte) {
            throw new EliminarExcepcion("Error al eliminar la persona: " + rte.getMessage(), "Persona");
        }
    }

    private void validarDatosObligatorios(Persona persona) {
        if (persona.getTipoIdentificacion() == null || persona.getNumeroIdentificacion() == null ||
                persona.getNombres() == null || persona.getGenero() == null || persona.getFechaNacimiento() == null ||
                persona.getNacionalidad() == null || persona.getEstadoCivil() == null || persona.getNivelEstudio() == null ||
                persona.getCorreoElectronico() == null || persona.getEstado() == null) {
            throw new CrearExcepcion("Todos los campos obligatorios deben estar presentes", "Persona");
        }
    }

    private void validarCorreo(String correo) {
        if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new CrearExcepcion("Correo electrónico inválido", "Persona");
        }
    }

    private void validarTipoIdentificacion(String tipoIdentificacion) {
        if (!tipoIdentificacion.equalsIgnoreCase("CEDULA") &&
                !tipoIdentificacion.equalsIgnoreCase("PASAPORTE")) {
            throw new CrearExcepcion("Tipo de identificación inválido (debe ser 'CEDULA' o 'PASAPORTE')", "Persona");
        }
    }

    private void validarNumeroIdentificacionUnico(String numeroIdentificacion) {
        if (personaRepositorio.findByNumeroIdentificacion(numeroIdentificacion).isPresent()) {
            throw new CrearExcepcion("Ya existe una persona con ese número de identificación", "Persona");
        }
    }

    private void validarLongitudNumeroIdentificacion(String numeroIdentificacion) {
        if (numeroIdentificacion.length() > 10) {
            throw new CrearExcepcion("Número de identificación excede longitud permitida", "Persona");
        }
    }

    private void validarFechaNacimiento(Date fechaNacimiento) {
        if (fechaNacimiento.after(new Date())) {
            throw new CrearExcepcion("La fecha de nacimiento no puede ser en el futuro", "Persona");
        }
    }
}
