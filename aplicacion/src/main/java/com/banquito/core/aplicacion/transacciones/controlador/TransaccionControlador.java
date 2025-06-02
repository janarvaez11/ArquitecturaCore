package com.banquito.core.aplicacion.transacciones.controlador;

import com.banquito.core.aplicacion.transacciones.modelo.Transaccion;
import com.banquito.core.aplicacion.transacciones.servicio.TransaccionServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionControlador {

    private final TransaccionServicio transaccionServicio;

    public TransaccionControlador(TransaccionServicio transaccionServicio) {
        this.transaccionServicio = transaccionServicio;
    }

    @GetMapping
    public ResponseEntity<List<Transaccion>> getAllTransacciones() {
        List<Transaccion> transacciones = transaccionServicio.findAll();
        return ResponseEntity.ok(transacciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> getTransaccionById(@PathVariable Integer id) {
        Transaccion transaccion = transaccionServicio.findById(id);
        return ResponseEntity.ok(transaccion);
    }

    @PostMapping
    public ResponseEntity<Transaccion> createTransaccion(@RequestBody Transaccion transaccion) {
        Transaccion nuevaTransaccion = transaccionServicio.create(transaccion);
        return ResponseEntity.ok(nuevaTransaccion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaccion> updateTransaccion(@PathVariable Integer id, @RequestBody Transaccion transaccion) {
        Transaccion transaccionActualizada = transaccionServicio.update(id, transaccion);
        return ResponseEntity.ok(transaccionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaccion(@PathVariable Integer id) {
        transaccionServicio.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<Transaccion> completarTransaccion(@PathVariable Integer id) {
        Transaccion transaccionCompletada = transaccionServicio.completarTransaccion(id);
        return ResponseEntity.ok(transaccionCompletada);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Transaccion> cancelarTransaccion(@PathVariable Integer id) {
        Transaccion transaccionCancelada = transaccionServicio.cancelarTransaccion(id);
        return ResponseEntity.ok(transaccionCancelada);
    }
}
