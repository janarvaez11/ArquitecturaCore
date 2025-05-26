package com.banquito.core.aplicacion.clientes.controlador;

import com.banquito.core.aplicacion.clientes.modelo.Empresa;
import com.banquito.core.aplicacion.clientes.servicio.EmpresaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaControlador {

    private final EmpresaServicio empresaServicio;

    public EmpresaControlador(EmpresaServicio empresaServicio) {
        this.empresaServicio = empresaServicio;
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> listarTodas() {
        List<Empresa> empresas = empresaServicio.buscarTodas();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{numero}")
    public ResponseEntity<Empresa> obtenerPorIdentificacion(@PathVariable String numero) {
        Empresa empresa = empresaServicio.buscarPorNumeroIdentificacion(numero);
        return ResponseEntity.ok(empresa);
    }


    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody Empresa empresa) {
        empresaServicio.crear(empresa);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping
    public ResponseEntity<Void> actualizar(@RequestBody Empresa empresa) {
        empresaServicio.actualizar(empresa);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        empresaServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
