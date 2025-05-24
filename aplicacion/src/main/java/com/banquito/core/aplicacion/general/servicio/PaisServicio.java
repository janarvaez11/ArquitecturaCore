package com.banquito.core.aplicacion.general.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.general.excepcion.PaisNoEncontradoExcepcion;
import com.banquito.core.aplicacion.general.excepcion.CrearPaisExcepcion;
import com.banquito.core.aplicacion.general.excepcion.ActualizarPaisExcepcion;
import com.banquito.core.aplicacion.general.modelo.Pais;
import com.banquito.core.aplicacion.general.repositorio.PaisRepositorio;

@Service
public class PaisServicio {

    private final PaisRepositorio paisRepositorio;

    public PaisServicio(PaisRepositorio paisRepositorio) {
        this.paisRepositorio = paisRepositorio;
    }

    @Transactional
    public void crearPais(Pais pais) {
        try {
            if (this.paisRepositorio.existsById(pais.getId())) {
                throw new CrearPaisExcepcion("Pais", "El país con ID " + pais.getId() + " ya existe.");
            }
            this.paisRepositorio.save(pais);
        } catch (RuntimeException e) {
            throw new CrearPaisExcepcion("Pais", "Error al crear el país: " + e.getMessage());
        }
    }

    @Transactional
    public void actualizarPais(Pais pais) {
        try {
            Optional<Pais> paisOptional = this.paisRepositorio.findById(pais.getId());
            if (paisOptional.isPresent()) {
                Pais paisExistente = paisOptional.get();
                paisExistente.setNombre(pais.getNombre());
                paisExistente.setCodigoTelefono(pais.getCodigoTelefono());
                this.paisRepositorio.save(paisExistente);
            } else {
                throw new PaisNoEncontradoExcepcion("No se encontró el país con ID: " + pais.getId());
            }
        } catch (RuntimeException e) {
            throw new ActualizarPaisExcepcion("Pais", "Error al actualizar el país: " + e.getMessage());
        }
    }

    public Pais obtenerPorId(String id) {
        return this.paisRepositorio.findById(id)
                .orElseThrow(() -> new PaisNoEncontradoExcepcion("País no encontrado con ID: " + id));
    }

    public List<Pais> listarTodos() {
        List<Pais> paises = this.paisRepositorio.findAll();
        if (paises.isEmpty()) {
            throw new PaisNoEncontradoExcepcion("No hay países registrados en el sistema.");
        }
        return paises;
    }

    public boolean existePais(String id) {
        return this.paisRepositorio.existsById(id);
    }

    public Pais obtenerPaisPorDefecto() {
        List<Pais> paises = this.paisRepositorio.findAll();
        if (paises.isEmpty()) {
            throw new PaisNoEncontradoExcepcion("No hay países registrados en el sistema.");
        }
        return paises.getFirst(); 
    }
}