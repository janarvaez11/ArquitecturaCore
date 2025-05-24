package com.banquito.core.aplicacion.clientes.controlador;

import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.clientes.servicio.ClienteServicio;
import com.banquito.core.aplicacion.clientes.excepcion.NoEncontradoExcepcion;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/clientes")
public class ClienteControlador {

    private final ClienteServicio clienteServicio;

    public ClienteControlador(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodos() {
        return ResponseEntity.ok(this.clienteServicio.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.clienteServicio.buscarPorId(id));
    }

    @ExceptionHandler({NoEncontradoExcepcion.class})
    public ResponseEntity<Void> manejarNoEncontrado() {
        return ResponseEntity.notFound().build();
    }
}
