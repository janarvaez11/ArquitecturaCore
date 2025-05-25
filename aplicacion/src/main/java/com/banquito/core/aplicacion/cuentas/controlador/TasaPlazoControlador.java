package com.banquito.core.aplicacion.cuentas.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.TasaPlazoNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.TasaPlazo;
import com.banquito.core.aplicacion.cuentas.servicio.TasaPlazoServicio;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/tasasPlazo")

public class TasaPlazoControlador {

    private final TasaPlazoServicio tasaPlazoServicio;

    public TasaPlazoControlador(TasaPlazoServicio tasaPlazoServicio) {
        this.tasaPlazoServicio = tasaPlazoServicio;
    }

    @GetMapping
    public ResponseEntity<List<TasaPlazo>> obtenerTodos() {
        return ResponseEntity.ok(tasaPlazoServicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TasaPlazo> obtenerPorId(@PathVariable Integer id) {
        try {
            TasaPlazo tasaPlazo = tasaPlazoServicio.findById(id);
            return ResponseEntity.ok(tasaPlazo);
        } catch (TasaPlazoNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody TasaPlazo tasaPlazo) {
        try {
            tasaPlazoServicio.create(tasaPlazo);
            return ResponseEntity.ok().build();
        } catch (CrearEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id, @RequestBody TasaPlazo tasaPlazo) {
        try {
            tasaPlazo.setIdPlazo(id);
            tasaPlazoServicio.update(tasaPlazo);
            return ResponseEntity.ok().build();
        } catch (ActualizarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            tasaPlazoServicio.delete(id);
            return ResponseEntity.ok().build();
        } catch (EliminarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
