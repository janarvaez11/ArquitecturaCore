package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.excepcion.ActualizarExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.CrearExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.EliminarExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.NoEncontradoExcepcion;
import com.banquito.core.aplicacion.clientes.modelo.Accionista;
import com.banquito.core.aplicacion.clientes.repositorio.AccionistaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        validarCamposObligatorios(accionista);
        try {
            accionistaRepositorio.save(accionista);
        } catch (RuntimeException rte) {
            throw new CrearExcepcion("Error al crear el accionista: " + rte.getMessage(), "Accionista");
        }
    }

    @Transactional
    public void actualizar(Accionista accionista) {
        if (accionista.getIdAccionista() == null) {
            throw new ActualizarExcepcion("ID de accionista no proporcionado", "Accionista");
        }

        validarCamposObligatorios(accionista);

        try {
            Accionista accionistaDB = accionistaRepositorio.findById(accionista.getIdAccionista())
                    .orElseThrow(() -> new ActualizarExcepcion("Accionista no encontrado con ID: " + accionista.getIdAccionista(), "Accionista"));

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
        } catch (RuntimeException rte) {
            throw new ActualizarExcepcion("Error al actualizar el accionista: " + rte.getMessage(), "Accionista");
        }
    }


    @Transactional
    public void eliminar(Integer id) {
        if (id == null) {
            throw new EliminarExcepcion("ID de accionista no proporcionado", "Accionista");
        }

        try {
            Accionista accionista = accionistaRepositorio.findById(id)
                    .orElseThrow(() -> new EliminarExcepcion("Accionista no encontrado con ID: " + id, "Accionista"));

            accionistaRepositorio.delete(accionista);
        } catch (RuntimeException rte) {
            throw new EliminarExcepcion("Error al eliminar el accionista: " + rte.getMessage(), "Accionista");
        }
    }

    private void validarCamposObligatorios(Accionista accionista) {
        if (accionista.getEmpresa() == null) {
            throw new CrearExcepcion("El accionista debe estar asociado a una empresa", "Accionista");
        }

        if (accionista.getParticipacion() == null) {
            throw new CrearExcepcion("La participación no puede ser nula", "Accionista");
        }

        if (accionista.getParticipacion().compareTo(BigDecimal.ZERO) <= 0 ||
                accionista.getParticipacion().compareTo(new BigDecimal("100.00")) > 0) {
            throw new CrearExcepcion("La participación debe ser mayor a 0 y menor o igual a 100", "Accionista");
        }

        if (accionista.getTipoEntidad() == null || accionista.getTipoEntidad().trim().isEmpty()) {
            throw new CrearExcepcion("El tipo de entidad es obligatorio", "Accionista");
        }
    }
}
