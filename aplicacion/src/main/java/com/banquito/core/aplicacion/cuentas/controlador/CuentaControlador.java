package com.banquito.core.aplicacion.cuentas.controlador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CuentaNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.Cuenta;
import com.banquito.core.aplicacion.cuentas.servicio.CuentaServicio;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/cuentas")
public class CuentaControlador {
    private final CuentaServicio cuentaServicio;

    public CuentaControlador(CuentaServicio cuentaServicio) {
        this.cuentaServicio = cuentaServicio;
    }

    @GetMapping
    public ResponseEntity<List<Cuenta>> obtenerTodos() {
        return ResponseEntity.ok(cuentaServicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerPorId(@PathVariable Integer id) {
        try {
            Cuenta cuenta = cuentaServicio.findById(id);
            return ResponseEntity.ok(cuenta);
        } catch (CuentaNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody Cuenta cuenta) {
        try {
            cuentaServicio.create(cuenta);
            return ResponseEntity.ok().build();
        } catch (CrearEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id, @RequestBody Cuenta cuenta) {
        try {
            cuenta.setIdCuenta(id);
            cuentaServicio.update(cuenta);
            return ResponseEntity.ok().build();
        } catch (ActualizarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            cuentaServicio.delete(id);
            return ResponseEntity.ok().build();
        } catch (EliminarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/tipo-cuenta/{idTipoCuenta}")
    public ResponseEntity<List<Cuenta>> buscarPorTipoCuenta(@PathVariable Integer idTipoCuenta) {
        try {
            return ResponseEntity.ok(cuentaServicio.findByTipoCuentaId(idTipoCuenta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Cuenta>> buscarPorEstado(@PathVariable String estado) {
        try {
            return ResponseEntity.ok(cuentaServicio.findByEstado(estado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}