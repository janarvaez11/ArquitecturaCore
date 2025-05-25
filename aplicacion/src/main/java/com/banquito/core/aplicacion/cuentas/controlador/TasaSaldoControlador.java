package com.banquito.core.aplicacion.cuentas.controlador;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.TasaSaldoNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.TasaSaldo;
import com.banquito.core.aplicacion.cuentas.servicio.TasaSaldoServicio;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/tasasSaldo")
public class TasaSaldoControlador {

    private final TasaSaldoServicio tasaSaldoServicio;

    public TasaSaldoControlador(TasaSaldoServicio tasaSaldoServicio) {
        this.tasaSaldoServicio = tasaSaldoServicio;
    }

    @GetMapping
    public ResponseEntity<List<TasaSaldo>> obtenerTodos() {
        return ResponseEntity.ok(tasaSaldoServicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TasaSaldo> obtenerPorId(@PathVariable Integer id) {
        try {
            TasaSaldo tasaSaldo = tasaSaldoServicio.findById(id);
            return ResponseEntity.ok(tasaSaldo);
        } catch (TasaSaldoNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody TasaSaldo tasaSaldo) {
        try {
            tasaSaldoServicio.create(tasaSaldo);
            return ResponseEntity.ok().build();
        } catch (CrearEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id, @RequestBody TasaSaldo tasaSaldo) {
        try {
            tasaSaldo.setIdSaldo(id);
            tasaSaldoServicio.update(tasaSaldo);
            return ResponseEntity.ok().build();
        } catch (ActualizarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            tasaSaldoServicio.delete(id);
            return ResponseEntity.ok().build();
        } catch (EliminarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }
}