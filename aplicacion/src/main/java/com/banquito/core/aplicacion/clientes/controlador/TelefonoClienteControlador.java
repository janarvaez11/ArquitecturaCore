package com.banquito.core.aplicacion.clientes.controlador;

import com.banquito.core.aplicacion.clientes.modelo.TelefonoCliente;
import com.banquito.core.aplicacion.clientes.servicio.TelefonoClienteServicio;
import com.banquito.core.aplicacion.clientes.excepcion.TelefonoNoEncontradoExcepcion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telefonos")
public class TelefonoClienteControlador {

    private final TelefonoClienteServicio servicio;

    public TelefonoClienteControlador(TelefonoClienteServicio servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/{idCliente}")
    public ResponseEntity<TelefonoCliente> crear(@PathVariable Integer idCliente, @RequestBody TelefonoCliente telefono) {
        return ResponseEntity.ok(servicio.crear(idCliente, telefono));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelefonoCliente> obtener(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(servicio.buscarPorId(id));
        } catch (TelefonoNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<TelefonoCliente>> listarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(servicio.obtenerPorCliente(idCliente));
    }

    @PatchMapping
    public ResponseEntity<TelefonoCliente> actualizar(@RequestBody TelefonoCliente telefono) {
        return ResponseEntity.ok(servicio.actualizar(telefono));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicio.eliminarLogico(id);
        return ResponseEntity.ok().build();
    }
}
