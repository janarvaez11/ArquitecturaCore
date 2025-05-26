package com.banquito.core.aplicacion.cuentas.controlador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.TipoCuentaNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.TipoCuenta;
import com.banquito.core.aplicacion.cuentas.servicio.TipoCuentaServicio;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/tipos-cuenta")
public class TipoCuentaControlador {
    private final TipoCuentaServicio tipoCuentaServicio;

    public TipoCuentaControlador(TipoCuentaServicio tipoCuentaServicio) {
        this.tipoCuentaServicio = tipoCuentaServicio;
    }

    @GetMapping
    public ResponseEntity<List<TipoCuenta>> obtenerTodos() {
        return ResponseEntity.ok(tipoCuentaServicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoCuenta> obtenerPorId(@PathVariable Integer id) {
        try {
            TipoCuenta tipoCuenta = tipoCuentaServicio.findById(id);
            return ResponseEntity.ok(tipoCuenta);
        } catch (TipoCuentaNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody TipoCuenta tipoCuenta) {
        try {
            tipoCuentaServicio.create(tipoCuenta);
            return ResponseEntity.ok().build();
        } catch (CrearEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id, @RequestBody TipoCuenta tipoCuenta) {
        try {
            tipoCuenta.setIdTipoCuenta(id);
            tipoCuentaServicio.update(tipoCuenta);
            return ResponseEntity.ok().build();
        } catch (ActualizarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            tipoCuentaServicio.delete(id);
            return ResponseEntity.ok().build();
        } catch (EliminarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<TipoCuenta>> buscarPorNombre(@RequestParam String nombre) {
        try {
            return ResponseEntity.ok(tipoCuentaServicio.buscarPorNombre(nombre));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/moneda/{idMoneda}")
    public ResponseEntity<List<TipoCuenta>> buscarPorMoneda(@PathVariable String idMoneda) {
        try {
            return ResponseEntity.ok(tipoCuentaServicio.buscarPorMoneda(idMoneda));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/tipo-cliente/{tipoCliente}")
    public ResponseEntity<List<TipoCuenta>> buscarPorTipoCliente(@PathVariable String tipoCliente) {
        try {
            return ResponseEntity.ok(tipoCuentaServicio.buscarPorTipoCliente(tipoCliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 