package com.banquito.core.aplicacion.clientes.controlador;

import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.clientes.modelo.ContactoTransaccionCliente;
import com.banquito.core.aplicacion.clientes.servicio.ContactoTransaccionClienteServicio;
import com.banquito.core.aplicacion.clientes.excepcion.ContactoNoEncontradoExcepcion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/contactos")
public class ContactoTransaccionClienteControlador {

    private final ContactoTransaccionClienteServicio servicio;

    public ContactoTransaccionClienteControlador(ContactoTransaccionClienteServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ContactoTransaccionCliente> obtenerPorCliente(@PathVariable Integer idCliente) {
        try {
            return ResponseEntity.ok(servicio.obtenerPorCliente(idCliente));
        } catch (ContactoNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idCliente}")
    public ResponseEntity<ContactoTransaccionCliente> crear(
            @PathVariable Integer idCliente,
            @RequestBody ContactoTransaccionCliente contacto) {
        return ResponseEntity.ok(servicio.crear(idCliente, contacto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactoTransaccionCliente> actualizar(
            @PathVariable Integer id,
            @RequestBody ContactoTransaccionCliente contacto) {

        Cliente cliente = new Cliente();
        cliente.setId(id);
        contacto.setCliente(cliente);

        return ResponseEntity.ok(servicio.actualizar(contacto));
    }

}
