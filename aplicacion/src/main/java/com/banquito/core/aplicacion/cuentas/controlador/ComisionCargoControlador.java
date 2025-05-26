package com.banquito.core.aplicacion.cuentas.controlador;

import com.banquito.core.aplicacion.cuentas.modelo.ComisionCargo;
import com.banquito.core.aplicacion.cuentas.servicio.ComisionCargoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comisiones-cargos")
public class ComisionCargoControlador {

    @Autowired
    private ComisionCargoServicio comisionCargoServicio;

    @GetMapping
    public ResponseEntity<Page<ComisionCargo>> listarComisionesCargos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(comisionCargoServicio.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComisionCargo> obtenerComisionCargo(@PathVariable Integer id) {
        return ResponseEntity.ok(comisionCargoServicio.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ComisionCargo> crearComisionCargo(@RequestBody ComisionCargo comisionCargo) {
        return ResponseEntity.ok(comisionCargoServicio.crear(comisionCargo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComisionCargo> actualizarComisionCargo(
            @PathVariable Integer id,
            @RequestBody ComisionCargo comisionCargo) {
        comisionCargo.setIdComisionCargo(id);
        return ResponseEntity.ok(comisionCargoServicio.actualizar(comisionCargo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComisionCargo(@PathVariable Integer id) {
        comisionCargoServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo-cuenta/{idTipoCuenta}")
    public ResponseEntity<List<ComisionCargo>> buscarPorTipoCuenta(@PathVariable Integer idTipoCuenta) {
        return ResponseEntity.ok(comisionCargoServicio.buscarPorTipoCuenta(idTipoCuenta));
    }

    @GetMapping("/tipo-comision/{tipoComision}")
    public ResponseEntity<List<ComisionCargo>> buscarPorTipoComision(@PathVariable String tipoComision) {
        return ResponseEntity.ok(comisionCargoServicio.buscarPorTipoComision(tipoComision));
    }

    @GetMapping("/vigentes")
    public ResponseEntity<List<ComisionCargo>> buscarComisionesVigentes() {
        return ResponseEntity.ok(comisionCargoServicio.buscarComisionesVigentes());
    }

    @GetMapping("/exentos")
    public ResponseEntity<List<ComisionCargo>> buscarComisionesExentas() {
        return ResponseEntity.ok(comisionCargoServicio.buscarComisionesExentas());
    }
} 