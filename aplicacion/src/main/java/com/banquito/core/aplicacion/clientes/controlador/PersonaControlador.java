package com.banquito.core.aplicacion.clientes.controlador;

import com.banquito.core.aplicacion.clientes.modelo.Persona;
import com.banquito.core.aplicacion.clientes.servicio.PersonaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaControlador {

    private final PersonaServicio personaServicio;

    public PersonaControlador(PersonaServicio personaServicio) {
        this.personaServicio = personaServicio;
    }

    @GetMapping
    public ResponseEntity<List<Persona>> listarTodos() {
        return ResponseEntity.ok(personaServicio.buscarTodos());
    }

    @GetMapping("/{numeroIdentificacion}")
    public ResponseEntity<Persona> obtenerPorNumeroIdentificacion(@PathVariable String numeroIdentificacion) {
        return ResponseEntity.ok(personaServicio.buscarPorNumeroIdentificacion(numeroIdentificacion));
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody Persona persona) {
        personaServicio.crear(persona);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping
    public ResponseEntity<Void> actualizar(@RequestBody Persona persona) {
        personaServicio.actualizar(persona);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        personaServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
