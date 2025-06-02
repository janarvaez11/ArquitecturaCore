package com.banquito.core.aplicacion.clientes.controlador;

import com.banquito.core.aplicacion.clientes.modelo.DireccionCliente;
import com.banquito.core.aplicacion.clientes.servicio.DireccionClienteServicio;
import com.banquito.core.aplicacion.clientes.excepcion.DireccionNoEncontradaExcepcion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/direcciones")
public class DireccionClienteControlador {

    private final DireccionClienteServicio servicio;

    public DireccionClienteControlador(DireccionClienteServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<DireccionCliente>> obtenerTodos() {
        return ResponseEntity.ok(servicio.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DireccionCliente> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(servicio.buscarPorId(id));
        } catch (DireccionNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DireccionCliente> crear(@RequestBody DireccionCliente direccion) {
        return ResponseEntity.ok(servicio.crear(direccion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DireccionCliente> actualizar(@PathVariable Integer id, @RequestBody DireccionCliente direccion) {
        direccion.setIdDireccion(id);
        return ResponseEntity.ok(servicio.modificar(direccion));
    }
}
