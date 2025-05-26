package com.banquito.core.aplicacion.prestamos.controlador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.aplicacion.prestamos.modelo.PrestamosClientes;
import com.banquito.core.aplicacion.prestamos.servicio.PrestamosClientesServicio;

@RestController
@RequestMapping("/api/v1/prestamos-clientes")
public class PrestamoClienteControlador {

    private final PrestamosClientesServicio prestamosClientesServicio;

    public PrestamoClienteControlador(PrestamosClientesServicio prestamosClientesServicio) {
        this.prestamosClientesServicio = prestamosClientesServicio;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamosClientes> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(this.prestamosClientesServicio.buscarPorId(id));
    }

    @GetMapping("/prestamo/{prestamoId}")
    public ResponseEntity<List<PrestamosClientes>> listarPorPrestamo(@PathVariable Integer prestamoId) {
        return ResponseEntity.ok(this.prestamosClientesServicio.buscarPorPrestamo(prestamoId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PrestamosClientes>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(this.prestamosClientesServicio.buscarPorEstado(estado));
    }

    //@GetMapping
    //public ResponseEntity<List<PrestamosClientes>> listarTodos() {
    //    return ResponseEntity.ok(this.prestamosClientesServicio.buscarTodos());
    //}

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody PrestamosClientes prestamoCliente) {
        this.prestamosClientesServicio.crear(prestamoCliente);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Void> aprobar(@PathVariable Integer id) {
        this.prestamosClientesServicio.aprobar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/desembolsar")
    public ResponseEntity<Void> desembolsar(@PathVariable Integer id) {
        this.prestamosClientesServicio.desembolsar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<Void> rechazar(@PathVariable Integer id) {
        this.prestamosClientesServicio.rechazar(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        this.prestamosClientesServicio.eliminar(id);
        return ResponseEntity.ok().build();
    }
}