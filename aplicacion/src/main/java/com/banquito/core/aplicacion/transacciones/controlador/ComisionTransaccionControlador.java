package com.banquito.core.aplicacion.transacciones.controlador;

import com.banquito.core.aplicacion.transacciones.modelo.ComisionTransaccion;
import com.banquito.core.aplicacion.transacciones.servicio.ComisionTransaccionServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comisiones-transaccion")
public class ComisionTransaccionControlador {

    private final ComisionTransaccionServicio comisionServicio;

    public ComisionTransaccionControlador(ComisionTransaccionServicio comisionServicio) {
        this.comisionServicio = comisionServicio;
    }

    @GetMapping
    public ResponseEntity<List<ComisionTransaccion>> getAllComisiones() {
        List<ComisionTransaccion> comisiones = comisionServicio.findAll();
        return ResponseEntity.ok(comisiones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComisionTransaccion> getComisionById(@PathVariable Integer id) {
        ComisionTransaccion comision = comisionServicio.findById(id);
        return ResponseEntity.ok(comision);
    }

    @PostMapping
    public ResponseEntity<ComisionTransaccion> createComision(@RequestBody ComisionTransaccion comision) {
        ComisionTransaccion nuevaComision = comisionServicio.create(comision);
        return ResponseEntity.ok(nuevaComision);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComision(@PathVariable Integer id) {
        comisionServicio.delete(id);
        return ResponseEntity.noContent().build();
    }
}
