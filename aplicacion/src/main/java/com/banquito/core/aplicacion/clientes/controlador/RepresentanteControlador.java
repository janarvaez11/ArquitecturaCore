package com.banquito.core.aplicacion.clientes.controlador;

import com.banquito.core.aplicacion.clientes.modelo.Representante;
import com.banquito.core.aplicacion.clientes.modelo.RepresentanteId;
import com.banquito.core.aplicacion.clientes.servicio.RepresentanteServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/representantes")
public class RepresentanteControlador {

    private final RepresentanteServicio representanteServicio;

    public RepresentanteControlador(RepresentanteServicio representanteServicio) {
        this.representanteServicio = representanteServicio;
    }

    @GetMapping("/{idEmpresa}/{idCliente}/{rol}")
    public ResponseEntity<Representante> obtenerPorId(
            @PathVariable Integer idEmpresa,
            @PathVariable Integer idCliente,
            @PathVariable String rol) {

        RepresentanteId id = new RepresentanteId(idEmpresa, idCliente, rol);
        Representante representante = representanteServicio.buscarPorId(id);
        return ResponseEntity.ok(representante);
    }

    @GetMapping
    public ResponseEntity<List<Representante>> listarTodos() {
        List<Representante> representantes = representanteServicio.buscarTodos();
        return ResponseEntity.ok(representantes);
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody Representante representante) {
        representanteServicio.crear(representante);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping
    public ResponseEntity<Void> actualizar(@RequestBody Representante representante) {
        representanteServicio.actualizar(representante);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idEmpresa}/{idCliente}/{rol}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer idEmpresa,
            @PathVariable Integer idCliente,
            @PathVariable String rol) {

        RepresentanteId id = new RepresentanteId(idEmpresa, idCliente, rol);
        representanteServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
