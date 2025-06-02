package com.banquito.core.aplicacion.clientes.controlador;

import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.clientes.servicio.ClienteServicio;
import com.banquito.core.aplicacion.clientes.excepcion.ClienteNoEncontradoExcepcion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteControlador {

    private final ClienteServicio servicio;

    public ClienteControlador(ClienteServicio servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(servicio.crearCliente(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(servicio.buscarPorId(id));
        } catch (ClienteNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/resumen/{id}")
    public ResponseEntity<Cliente> obtenerResumen(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(servicio.obtenerInformacionResumida(id));
        } catch (ClienteNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/completo/{id}")
    public ResponseEntity<Cliente> obtenerCompleto(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(servicio.obtenerInformacionCompleta(id));
        } catch (ClienteNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer id, @RequestBody Cliente actualizado) {
        actualizado.setId(id);
        return ResponseEntity.ok(servicio.actualizarCliente(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicio.eliminarClienteLogico(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos(@RequestParam(name = "estado", required = false) String estado) {
        if (estado != null) {
            return ResponseEntity.ok(servicio.obtenerPorEstado(estado));
        } else {
            return ResponseEntity.ok(servicio.obtenerTodos());
        }
    }

    @ExceptionHandler({ClienteNoEncontradoExcepcion.class})
    public ResponseEntity<String> manejarExcepcion(ClienteNoEncontradoExcepcion e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
