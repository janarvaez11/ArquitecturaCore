package com.banquito.core.aplicacion.clientes.controlador;

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

    @GetMapping("/{id}")
    public ResponseEntity<ContactoTransaccionCliente> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(servicio.buscarPorId(id));
        } catch (ContactoNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ContactoTransaccionCliente> crear(@RequestBody ContactoTransaccionCliente contacto) {
        return ResponseEntity.ok(servicio.crear(contacto));
    }

}
