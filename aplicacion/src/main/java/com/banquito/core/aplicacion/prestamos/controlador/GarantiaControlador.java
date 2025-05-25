package com.banquito.core.aplicacion.prestamos.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.GarantiaNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.Garantia;
import com.banquito.core.aplicacion.prestamos.servicio.GarantiaServicio;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/garantias")
public class GarantiaControlador {

    private final GarantiaServicio garantiaServicio;

    public GarantiaControlador(GarantiaServicio garantiaServicio) {
        this.garantiaServicio = garantiaServicio;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Garantia> obtenerPorId(@PathVariable Integer id) {
        try {
            Garantia garantia = garantiaServicio.findById(id);
            return ResponseEntity.ok(garantia);
        } catch (GarantiaNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Void> crear(@RequestBody Garantia garantia) {
        try {
            garantiaServicio.create(garantia);
            return ResponseEntity.ok().build();
        } catch (CrearEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id, @RequestBody Garantia garantia) {
        try {
            garantia.setId(id);
            garantiaServicio.update(garantia);
            return ResponseEntity.ok().build();
        } catch (ActualizarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            garantiaServicio.delete(id);
            return ResponseEntity.ok().build();
        } catch (EliminarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 