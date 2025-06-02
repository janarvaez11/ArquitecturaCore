package com.banquito.core.aplicacion.transacciones.controlador;

import com.banquito.core.aplicacion.transacciones.modelo.EstadoTransaccion;
import com.banquito.core.aplicacion.transacciones.servicio.EstadoTransaccionServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estados-transaccion")
public class EstadoTransaccionControlador {

    private final EstadoTransaccionServicio estadoServicio;

    public EstadoTransaccionControlador(EstadoTransaccionServicio estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    @GetMapping
    public ResponseEntity<List<EstadoTransaccion>> getAllEstados() {
        List<EstadoTransaccion> estados = estadoServicio.findAll();
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoTransaccion> getEstadoById(@PathVariable String id) {
        EstadoTransaccion estado = estadoServicio.findById(id);
        return ResponseEntity.ok(estado);
    }

    @PostMapping
    public ResponseEntity<EstadoTransaccion> createEstado(@RequestBody EstadoTransaccion estado) {
        EstadoTransaccion nuevoEstado = estadoServicio.create(estado);
        return ResponseEntity.ok(nuevoEstado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoTransaccion> updateEstado(@PathVariable String id, @RequestBody EstadoTransaccion estado) {
        EstadoTransaccion estadoActualizado = estadoServicio.update(id, estado);
        return ResponseEntity.ok(estadoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstado(@PathVariable String id) {
        estadoServicio.delete(id);
        return ResponseEntity.noContent().build();
    }
}
