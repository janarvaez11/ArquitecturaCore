package com.banquito.core.aplicacion.general.controlador;

import com.banquito.core.aplicacion.general.modelo.Pais;
import com.banquito.core.aplicacion.general.servicio.PaisServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paises")
public class PaisControlador {

    private final PaisServicio paisServicio;

    public PaisControlador(PaisServicio paisServicio) {
        this.paisServicio = paisServicio;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pais> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(paisServicio.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Pais>> listarTodos() {
        return ResponseEntity.ok(paisServicio.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody Pais pais) {
        paisServicio.crearPais(pais);
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<Void> actualizar(@RequestBody Pais pais) {
        paisServicio.actualizarPais(pais);
        return ResponseEntity.noContent().build();
    }
}