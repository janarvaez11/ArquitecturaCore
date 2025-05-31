package com.banquito.core.aplicacion.general.controlador;

import com.banquito.core.aplicacion.general.modelo.LocacionGeografica;
import com.banquito.core.aplicacion.general.servicio.LocacionGeograficaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locaciones")
public class LocacionGeograficaControlador {

    private final LocacionGeograficaServicio locacionGeograficaServicio;

    public LocacionGeograficaControlador(LocacionGeograficaServicio locacionGeograficaServicio) {
        this.locacionGeograficaServicio = locacionGeograficaServicio;
    }
    
    @GetMapping
    public ResponseEntity<List<LocacionGeografica>> listarTodas() {
        return ResponseEntity.ok(locacionGeograficaServicio.listarTodas());
    }

}