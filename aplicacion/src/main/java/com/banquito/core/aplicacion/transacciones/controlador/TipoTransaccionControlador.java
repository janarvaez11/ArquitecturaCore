package com.banquito.core.aplicacion.transacciones.controlador;

import com.banquito.core.aplicacion.transacciones.modelo.TipoTransaccion;
import com.banquito.core.aplicacion.transacciones.servicio.TipoTransaccionServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-transaccion")
public class TipoTransaccionControlador {

    private final TipoTransaccionServicio tipoServicio;

    public TipoTransaccionControlador(TipoTransaccionServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    @GetMapping
    public ResponseEntity<List<TipoTransaccion>> getAllTipos() {
        List<TipoTransaccion> tipos = tipoServicio.findAll();
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoTransaccion> getTipoById(@PathVariable Integer id) {
        TipoTransaccion tipo = tipoServicio.findById(id);
        return ResponseEntity.ok(tipo);
    }

    @PostMapping
    public ResponseEntity<TipoTransaccion> createTipo(@RequestBody TipoTransaccion tipo) {
        TipoTransaccion nuevoTipo = tipoServicio.create(tipo);
        return ResponseEntity.ok(nuevoTipo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoTransaccion> updateTipo(@PathVariable Integer id, @RequestBody TipoTransaccion tipo) {
        TipoTransaccion tipoActualizado = tipoServicio.update(id, tipo);
        return ResponseEntity.ok(tipoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipo(@PathVariable Integer id) {
        tipoServicio.delete(id);
        return ResponseEntity.noContent().build();
    }
}
