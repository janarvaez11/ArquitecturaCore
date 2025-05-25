package com.banquito.core.aplicacion.general.servicio;

import com.banquito.core.aplicacion.general.modelo.EntidadBancaria;
import com.banquito.core.aplicacion.general.repositorio.EntidadBancariaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EntidadBancariaServicio {
    private final EntidadBancariaRepositorio repositorio;

    public EntidadBancariaServicio(EntidadBancariaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public EntidadBancaria findById(Integer id){
        Optional<EntidadBancaria> entidadBancariaOptional = this.repositorio.findById(id);
        if (entidadBancariaOptional.isPresent()) {
            return entidadBancariaOptional.get();
        } else {
            throw new EntidadBancariaNoEncontradaException("EntidadBancaria", "El ID: " + id + " no corresponde a ninguna registro");
        }
    }

    @Transactional
    public void create(EntidadBancaria entidadBancaria) {
        try {
            this.repositorio.save(entidadBancaria);
        } catch (RuntimeException rte) {
            throw new CrearEntidadBancariaException("EntidadBancaria", "Error al crear la entidad bancaria. Texto del error: " + rte.getMessage());
        }
    }
}
