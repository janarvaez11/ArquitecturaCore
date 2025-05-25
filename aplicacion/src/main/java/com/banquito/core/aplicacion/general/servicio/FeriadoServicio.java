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

    private final FeriadoRepositorio feriadorepositorio;
    private final PaisRepositorio paisrepositorio;
    private final LocacionGeograficaRepositorio locaciongeograficarepositorio;

    public FeriadoServicio(FeriadoRepositorio feriadorepositorio, PaisRepositorio paisrepositorio, LocacionGeograficaRepositorio locaciongeograficarepositorio) {
        this.feriadorepositorio = feriadorepositorio;
        this.paisrepositorio = paisrepositorio;
        this.locaciongeograficarepositorio = locaciongeograficarepositorio;
    }


    @Transactional
    public void crearFeriadoPorPais(Feriado feriado, String paisId) {
        Pais pais = paisrepositorio.findById(paisId)
                .orElseThrow(() -> new IllegalArgumentException("País no encontrado"));
        feriado.setPais(pais);
        feriado.setLocacion(null);
        feriadorepositorio.save(feriado);
    }

    @Transactional
    public void crearFeriadoPorLocacion(Feriado feriado, Integer locacionId) {
        LocacionGeografica locacion = locaciongeograficarepositorio.findById(locacionId)
                .orElseThrow(() -> new IllegalArgumentException("Locación no encontrada"));
        feriado.setLocacion(locacion);
        feriado.setPais(locacion.getPais());
        feriadorepositorio.save(feriado);
    }



}
