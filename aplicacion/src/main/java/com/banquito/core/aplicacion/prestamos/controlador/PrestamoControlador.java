package com.banquito.core.aplicacion.prestamos.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.PrestamoNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.Prestamo;
import com.banquito.core.aplicacion.prestamos.servicio.PrestamoServicio;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/prestamos")
public class PrestamoControlador {

    private final PrestamoServicio prestamoServicio;

    public PrestamoControlador(PrestamoServicio prestamoServicio) {
        this.prestamoServicio = prestamoServicio;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPorId(@PathVariable Integer id) {
        try {
            Prestamo prestamo = prestamoServicio.findById(id);
            return ResponseEntity.ok(prestamo);
        } catch (PrestamoNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody Prestamo prestamo) {
        try {
            prestamoServicio.create(prestamo);
            return ResponseEntity.ok().build();
        } catch (CrearEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id, @RequestBody Prestamo prestamo) {
        try {
            prestamo.setId(id);
            prestamoServicio.update(prestamo);
            return ResponseEntity.ok().build();
        } catch (ActualizarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            prestamoServicio.delete(id);
            return ResponseEntity.ok().build();
        } catch (EliminarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }
}