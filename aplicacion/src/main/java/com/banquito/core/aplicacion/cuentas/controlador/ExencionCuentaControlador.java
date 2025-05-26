package com.banquito.core.aplicacion.cuentas.controlador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.ExencionCuentaNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.ExencionCuenta;
import com.banquito.core.aplicacion.cuentas.servicio.ExencionCuentaServicio;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/exenciones-cuentas")
public class ExencionCuentaControlador {

    private final ExencionCuentaServicio exencionCuentaServicio;

    public ExencionCuentaControlador(ExencionCuentaServicio exencionCuentaServicio) {
        this.exencionCuentaServicio = exencionCuentaServicio;
    }

    @GetMapping
    public ResponseEntity<List<ExencionCuenta>> obtenerTodos() {
        return ResponseEntity.ok(exencionCuentaServicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExencionCuenta> obtenerPorId(@PathVariable Integer id) {
        try {
            ExencionCuenta exencionCuenta = exencionCuentaServicio.findById(id);
            return ResponseEntity.ok(exencionCuenta);
        } catch (ExencionCuentaNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody ExencionCuenta exencionCuenta) {
        try {
            exencionCuentaServicio.create(exencionCuenta);
            return ResponseEntity.ok().build();
        } catch (CrearEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id, @RequestBody ExencionCuenta exencionCuenta) {
        try {
            exencionCuenta.setIdExencion(id);
            exencionCuentaServicio.update(exencionCuenta);
            return ResponseEntity.ok().build();
        } catch (ActualizarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            exencionCuentaServicio.delete(id);
            return ResponseEntity.ok().build();
        } catch (EliminarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }
}