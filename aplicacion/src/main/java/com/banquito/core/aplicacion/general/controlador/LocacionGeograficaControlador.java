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

    @GetMapping("/{id}")
    public ResponseEntity<LocacionGeografica> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(locacionGeograficaServicio.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<LocacionGeografica>> listarTodas() {
        return ResponseEntity.ok(locacionGeograficaServicio.listarTodas());
    }

    @GetMapping("/activas")
    public ResponseEntity<List<LocacionGeografica>> listarActivas() {
        return ResponseEntity.ok(locacionGeograficaServicio.listarActivas());
    }

    @GetMapping("/pais/{idPais}")
    public ResponseEntity<List<LocacionGeografica>> listarPorPais(@PathVariable String idPais) {
        return ResponseEntity.ok(locacionGeograficaServicio.listarPorPais(idPais));
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<LocacionGeografica>> listarPorNivel(@PathVariable Integer nivel) {
        return ResponseEntity.ok(locacionGeograficaServicio.listarPorNivel(nivel));
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody LocacionGeografica locacion) {
        locacionGeograficaServicio.crearLocacion(locacion);
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<Void> actualizar(@RequestBody LocacionGeografica locacion) {
        locacionGeograficaServicio.actualizarLocacion(locacion);
        return ResponseEntity.noContent().build();
    }
}