package com.banquito.core.aplicacion.cuentas.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.banquito.core.aplicacion.cuentas.modelo.CuentaCliente;
import com.banquito.core.aplicacion.cuentas.servicio.CuentaClienteServicio;

@RestController
@RequestMapping("/api/v1/cuentas-cliente")
public class CuentaClienteControlador {

    @Autowired
    private CuentaClienteServicio cuentaClienteServicio;

    @GetMapping
    public ResponseEntity<List<CuentaCliente>> listarCuentasCliente() {
        return ResponseEntity.ok(cuentaClienteServicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaCliente> obtenerCuentaCliente(@PathVariable Integer id) {
        return ResponseEntity.ok(cuentaClienteServicio.findById(id));
    }

    @PostMapping
    public ResponseEntity<CuentaCliente> crearCuentaCliente(@RequestBody CuentaCliente cuentaCliente) {
        cuentaClienteServicio.create(cuentaCliente);
        return ResponseEntity.ok(cuentaCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaCliente> actualizarCuentaCliente(
            @PathVariable Integer id,
            @RequestBody CuentaCliente cuentaCliente) {
        cuentaCliente.setIdCuentaCliente(id);
        cuentaClienteServicio.update(cuentaCliente);
        return ResponseEntity.ok(cuentaCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuentaCliente(@PathVariable Integer id) {
        cuentaClienteServicio.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<CuentaCliente>> buscarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(cuentaClienteServicio.findByClienteId(idCliente));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CuentaCliente>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(cuentaClienteServicio.findByEstado(estado));
    }

    @GetMapping("/saldo-disponible")
    public ResponseEntity<List<CuentaCliente>> buscarPorSaldoDisponibleMayorQue(
            @RequestParam Double saldoMinimo) {
        return ResponseEntity.ok(cuentaClienteServicio.findBySaldoDisponibleGreaterThan(saldoMinimo));
    }

    @GetMapping("/numero-cuenta/{numeroCuenta}")
    public ResponseEntity<CuentaCliente> buscarPorNumeroCuenta(@PathVariable String numeroCuenta) {
        return ResponseEntity.ok(cuentaClienteServicio.findByNumeroCuenta(numeroCuenta));
    }
} 