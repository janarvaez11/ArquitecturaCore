package com.banquito.core.aplicacion.clientes.controlador;

import com.banquito.core.aplicacion.clientes.modelo.DireccionCliente;
import com.banquito.core.aplicacion.clientes.servicio.DireccionClienteServicio;
import com.banquito.core.aplicacion.clientes.excepcion.DireccionNoEncontradaExcepcion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionClienteControlador {

    private final DireccionClienteServicio servicio;

    public DireccionClienteControlador(DireccionClienteServicio servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/{idCliente}")
    public ResponseEntity<DireccionCliente> crear(@PathVariable Integer idCliente, @RequestBody DireccionCliente direccion) {
        return ResponseEntity.ok(servicio.crear(idCliente, direccion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DireccionCliente> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(servicio.buscarPorId(id));
        } catch (DireccionNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<DireccionCliente>> listarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(servicio.obtenerPorCliente(idCliente));
    }

    @PatchMapping
    public ResponseEntity<DireccionCliente> actualizar(@RequestBody DireccionCliente direccion) {
        return ResponseEntity.ok(servicio.actualizar(direccion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicio.eliminarLogica(id);
        return ResponseEntity.ok().build();
    }
}
