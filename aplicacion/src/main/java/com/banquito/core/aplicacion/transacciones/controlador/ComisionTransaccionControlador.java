package com.banquito.core.aplicacion.transacciones.controlador;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.aplicacion.transacciones.modelo.ComisionTransaccion;
import com.banquito.core.aplicacion.transacciones.servicio.ComisionTransaccionServicio;

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

    @GetMapping("/tipo/{tipoComision}")
    public ResponseEntity<List<ComisionTransaccion>> getComisionesByTipo(@PathVariable String tipoComision) {
        List<ComisionTransaccion> comisiones = comisionServicio.findByTipoComision(tipoComision);
        return ResponseEntity.ok(comisiones);
    }

    @GetMapping("/{id}/calcular")
    public ResponseEntity<BigDecimal> calcularComision(
            @PathVariable Integer id,
            @RequestParam BigDecimal montoTransaccion) {
        BigDecimal comisionCalculada = comisionServicio.calcularComision(montoTransaccion, id);
        return ResponseEntity.ok(comisionCalculada);
    }

    @PostMapping
    public ResponseEntity<ComisionTransaccion> createComision(@RequestBody ComisionTransaccion comision) {
        ComisionTransaccion nuevaComision = comisionServicio.create(comision);
        return ResponseEntity.ok(nuevaComision);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComisionTransaccion> updateComision(
            @PathVariable Integer id,
            @RequestBody ComisionTransaccion comision) {
        ComisionTransaccion comisionActualizada = comisionServicio.update(id, comision);
        return ResponseEntity.ok(comisionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComision(@PathVariable Integer id) {
        comisionServicio.delete(id);
        return ResponseEntity.noContent().build();
    }
}
