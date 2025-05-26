package com.banquito.core.aplicacion.clientes.controlador;

import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.clientes.servicio.ClienteServicio;
import com.banquito.core.aplicacion.clientes.excepcion.ClienteNoEncontradoExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.CrearClienteExcepcion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/clientes")
public class ClienteControlador {

    private final ClienteServicio servicio;

    public ClienteControlador(ClienteServicio servicio) {
        this.servicio = servicio;
    }

    // Obtener los primeros 10 clientes
    @GetMapping("/primeros10")
    public ResponseEntity<List<Cliente>> obtenerPrimeros10() {
        return ResponseEntity.ok(servicio.buscarPrimeros10());
    }

    // Obtener un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(servicio.buscarPorId(id));
        } catch (ClienteNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener un cliente completo (con direcciones y teléfonos activos)
    @GetMapping("/completo/{id}")
    public ResponseEntity<Cliente> obtenerClienteCompleto(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(servicio.obtenerClienteCompletoPorId(id));
        } catch (ClienteNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cliente cliente) {
        try {
            Cliente nuevo = servicio.crear(cliente);
            return ResponseEntity.ok(nuevo);
        } catch (CrearClienteExcepcion e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // Actualizar un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            cliente.setIdCliente(id);
            Cliente actualizado = servicio.modificar(cliente);
            return ResponseEntity.ok(actualizado);
        } catch (ClienteNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error al actualizar el cliente: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}