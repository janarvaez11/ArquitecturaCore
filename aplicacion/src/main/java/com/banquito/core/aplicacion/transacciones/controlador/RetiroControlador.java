package com.banquito.core.aplicacion.transacciones.controlador;

import com.banquito.core.aplicacion.transacciones.modelo.Retiro;
import com.banquito.core.aplicacion.transacciones.servicio.RetiroServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/retiros")
public class RetiroControlador {

    private final RetiroServicio retiroServicio;

    public RetiroControlador(RetiroServicio retiroServicio) {
        this.retiroServicio = retiroServicio;
    }

    @GetMapping
    public ResponseEntity<List<Retiro>> getAllRetiros() {
        List<Retiro> retiros = retiroServicio.findAll();
        return ResponseEntity.ok(retiros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Retiro> getRetiroById(@PathVariable Integer id) {
        Retiro retiro = retiroServicio.findById(id);
        return ResponseEntity.ok(retiro);
    }

    @PostMapping
    public ResponseEntity<Retiro> createRetiro(@RequestBody Retiro retiro) {
        Retiro nuevoRetiro = retiroServicio.create(retiro);
        return ResponseEntity.ok(nuevoRetiro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Retiro> updateRetiro(@PathVariable Integer id, @RequestBody Retiro retiro) {
        Retiro retiroActualizado = retiroServicio.update(id, retiro);
        return ResponseEntity.ok(retiroActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRetiro(@PathVariable Integer id) {
        retiroServicio.delete(id);
        return ResponseEntity.noContent().build();
    }
}
