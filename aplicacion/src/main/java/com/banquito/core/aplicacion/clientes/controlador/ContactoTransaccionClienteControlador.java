package com.banquito.core.aplicacion.clientes.controlador;

import com.banquito.core.aplicacion.clientes.dto.ContactoTransaccionClienteDTO;
import com.banquito.core.aplicacion.clientes.controlador.mapper.ContactoTransaccionClienteMapper;
import com.banquito.core.aplicacion.clientes.modelo.ContactoTransaccionCliente;
import com.banquito.core.aplicacion.clientes.servicio.ContactoTransaccionClienteServicio;
import com.banquito.core.aplicacion.clientes.excepcion.NoEncontradoExcepcion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/contactos-transaccion")
public class ContactoTransaccionClienteControlador {

    private final ContactoTransaccionClienteServicio servicio;

    public ContactoTransaccionClienteControlador(ContactoTransaccionClienteServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<ContactoTransaccionClienteDTO>> obtenerTodos() {
        List<ContactoTransaccionClienteDTO> resultado = this.servicio.buscarTodos()
                .stream()
                .map(ContactoTransaccionClienteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactoTransaccionClienteDTO> obtenerPorId(@PathVariable("id") Integer id) {
        ContactoTransaccionCliente contacto = this.servicio.buscarPorId(id);
        return ResponseEntity.ok(ContactoTransaccionClienteMapper.toDTO(contacto));
    }

    @ExceptionHandler(NoEncontradoExcepcion.class)
    public ResponseEntity<Void> manejarNoEncontrado() {
        return ResponseEntity.notFound().build();
    }
}

