package com.banquito.core.aplicacion.clientes.controlador;

import com.banquito.core.aplicacion.clientes.modelo.TelefonoCliente;
import com.banquito.core.aplicacion.clientes.servicio.TelefonoClienteServicio;
import com.banquito.core.aplicacion.clientes.excepcion.TelefonoNoEncontradoExcepcion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/telefonos")
public class TelefonoClienteControlador {

    private final TelefonoClienteServicio servicio;

    public TelefonoClienteControlador(TelefonoClienteServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<TelefonoCliente>> obtenerTodos() {
        return ResponseEntity.ok(servicio.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelefonoCliente> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(servicio.buscarPorId(id));
        } catch (TelefonoNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TelefonoCliente> crear(@RequestBody TelefonoCliente telefono) {
        return ResponseEntity.ok(servicio.crear(telefono));
    }


}
