package com.banquito.core.aplicacion.general.controlador;

import com.banquito.core.aplicacion.general.modelo.EstructuraGeografica;
import com.banquito.core.aplicacion.general.servicio.EstructuraGeograficaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estructurasGeograficas")
public class EstructuraGeograficaControlador {

    private final EstructuraGeograficaServicio servicio;

    public EstructuraGeograficaControlador(EstructuraGeograficaServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<EstructuraGeografica>> listarTodas() {
        return ResponseEntity.ok(servicio.listarTodas());
    }

}
