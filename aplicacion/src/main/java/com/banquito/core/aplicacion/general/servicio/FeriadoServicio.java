package com.banquito.core.aplicacion.general.servicio;

import com.banquito.core.aplicacion.general.excepcion.CrearEntidadException;
import com.banquito.core.aplicacion.general.excepcion.FeriadoNoEncontradoException;
import com.banquito.core.aplicacion.general.modelo.Feriado;
import com.banquito.core.aplicacion.general.repositorio.FeriadoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FeriadoServicio {

    private final FeriadoRepositorio repositorio;

    public FeriadoServicio(FeriadoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Feriado findById(Integer id){
        Optional<Feriado> feriadoOptional = this.repositorio.findById(id);
        if (feriadoOptional.isPresent()) {
            return feriadoOptional.get();
        } else {
            throw new FeriadoNoEncontradoException("El id: " + id + " no corresponde a ningun Feriado");
        }
    }

    @Transactional
    public void create(Feriado feriado) {
        try {
            this.repositorio.save(feriado);
        } catch (RuntimeException rte) {
            throw new CrearEntidadException("Feriado", "Error al crear el feriado. Texto del error: " + rte.getMessage());
        }
    }



}
