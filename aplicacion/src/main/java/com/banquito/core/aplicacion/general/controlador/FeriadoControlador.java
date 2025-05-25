package com.banquito.core.aplicacion.general.controlador;

import com.banquito.core.aplicacion.general.modelo.Feriado;
import com.banquito.core.aplicacion.general.servicio.FeriadoServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/feriados")
public class FeriadoControlador {

    private final FeriadoServicio feriadoServicio;

    public FeriadoControlador(FeriadoServicio feriadoServicio) {
        this.feriadoServicio = feriadoServicio;
    }


    @PostMapping("/pais/{paisId}")
    public ResponseEntity<Feriado> crearPorPais(@PathVariable String paisId, @RequestBody Feriado feriado) {
        feriadoServicio.crearFeriadoPorPais(feriado, paisId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/locacion/{locacionId}")
    public ResponseEntity<Feriado> crearPorLocacion(@PathVariable Integer locacionId, @RequestBody Feriado feriado) {
        feriadoServicio.crearFeriadoPorLocacion(feriado, locacionId);
        return ResponseEntity.ok().build();
    }

}
