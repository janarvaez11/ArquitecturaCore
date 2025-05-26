package com.banquito.core.aplicacion.clientes.controlador;

import com.banquito.core.aplicacion.clientes.modelo.Accionista;
import com.banquito.core.aplicacion.clientes.servicio.AccionistaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accionistas")
public class AccionistaControlador {

    private final AccionistaServicio accionistaServicio;

    public AccionistaControlador(AccionistaServicio accionistaServicio) {
        this.accionistaServicio = accionistaServicio;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accionista> obtenerPorId(@PathVariable Integer id) {
        Accionista accionista = accionistaServicio.buscarPorId(id);
        return ResponseEntity.ok(accionista);
    }

    @GetMapping
    public ResponseEntity<List<Accionista>> listarTodos() {
        List<Accionista> accionistas = accionistaServicio.buscarTodos();
        return ResponseEntity.ok(accionistas);
    }


    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody Accionista accionista) {
        accionistaServicio.crear(accionista);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping
    public ResponseEntity<Void> actualizar(@RequestBody Accionista accionista) {
        accionistaServicio.actualizar(accionista);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        accionistaServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
