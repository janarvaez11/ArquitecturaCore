package com.banquito.core.aplicacion.prestamos.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.BusquedaExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.ComisionPrestamoNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.ComisionPrestamo;
import com.banquito.core.aplicacion.prestamos.modelo.CondicionComision;
import com.banquito.core.aplicacion.prestamos.modelo.PrestamoComisionCargo;
import com.banquito.core.aplicacion.prestamos.servicio.ComisionPrestamoServicio;
import com.banquito.core.aplicacion.prestamos.servicio.CondicionComisionServicio;
import com.banquito.core.aplicacion.prestamos.servicio.PrestamoComisionCargoServicio;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/prestamos/comisiones")
public class ComisionPrestamoControlador {

    private final ComisionPrestamoServicio comisionPrestamoServicio;
    private final CondicionComisionServicio condicionComisionServicio;
    private final PrestamoComisionCargoServicio prestamoComisionCargoServicio;

    public ComisionPrestamoControlador(
            ComisionPrestamoServicio comisionPrestamoServicio,
            CondicionComisionServicio condicionComisionServicio,
            PrestamoComisionCargoServicio prestamoComisionCargoServicio) {
        this.comisionPrestamoServicio = comisionPrestamoServicio;
        this.condicionComisionServicio = condicionComisionServicio;
        this.prestamoComisionCargoServicio = prestamoComisionCargoServicio;
    }

    // Endpoints para Comisiones
    @GetMapping
    public ResponseEntity<List<ComisionPrestamo>> listarComisiones() {
        return ResponseEntity.ok(comisionPrestamoServicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComisionPrestamo> obtenerComision(@PathVariable Integer id) {
        try {
            ComisionPrestamo comision = comisionPrestamoServicio.findById(id);
            return ResponseEntity.ok(comision);
        } catch (ComisionPrestamoNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ComisionPrestamo> crearComision(@RequestBody ComisionPrestamo comision) {
        try {
            comisionPrestamoServicio.create(comision);
            return ResponseEntity.ok(comision);
        } catch (CrearEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComisionPrestamo> actualizarComision(
            @PathVariable Integer id,
            @RequestBody ComisionPrestamo comision) {
        try {
            comision.setId(id);
            comisionPrestamoServicio.update(comision);
            return ResponseEntity.ok(comision);
        } catch (ActualizarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoints para Condiciones de Comisión
    @GetMapping("/condiciones")
    public ResponseEntity<List<CondicionComision>> listarCondiciones() {
        return ResponseEntity.ok(condicionComisionServicio.findAll());
    }

    @GetMapping("/condiciones/{id}")
    public ResponseEntity<CondicionComision> obtenerCondicion(@PathVariable Integer id) {
        return ResponseEntity.ok(condicionComisionServicio.findById(id));
    }

    @GetMapping("/condiciones/tipo/{tipoCondicion}")
    public ResponseEntity<List<CondicionComision>> listarCondicionesPorTipo(
            @PathVariable String tipoCondicion) {
        return ResponseEntity.ok(condicionComisionServicio.findByTipoCondicion(tipoCondicion));
    }

    @PostMapping("/condiciones")
    public ResponseEntity<CondicionComision> crearCondicion(@RequestBody CondicionComision condicion) {
        condicionComisionServicio.create(condicion);
        return ResponseEntity.ok(condicion);
    }

    @PutMapping("/condiciones/{id}")
    public ResponseEntity<CondicionComision> actualizarCondicion(
            @PathVariable Integer id,
            @RequestBody CondicionComision condicion) {
        condicion.setId(id);
        condicionComisionServicio.update(condicion);
        return ResponseEntity.ok(condicion);
    }

    @DeleteMapping("/condiciones/{id}")
    public ResponseEntity<Void> eliminarCondicion(@PathVariable Integer id) {
        condicionComisionServicio.delete(id);
        return ResponseEntity.ok().build();
    }

    // Endpoints para Asignación de Comisiones a Préstamos
    @GetMapping("/prestamo/{idPrestamo}")
    public ResponseEntity<List<Map<String, Object>>> obtenerComisionesPorPrestamo(
            @PathVariable Integer idPrestamo) {
        return ResponseEntity.ok(prestamoComisionCargoServicio.findComisionesByPrestamo(idPrestamo));
    }

    @PostMapping("/prestamo")
    public ResponseEntity<PrestamoComisionCargo> asignarComisionAPrestamo(
            @RequestBody PrestamoComisionCargo prestamoComisionCargo) {
        prestamoComisionCargoServicio.create(prestamoComisionCargo);
        return ResponseEntity.ok(prestamoComisionCargo);
    }

    // Endpoint para obtener tipos de condiciones disponibles
    @GetMapping("/tipos-condicion")
    public ResponseEntity<Map<String, List<String>>> obtenerTiposCondicion() {
        Map<String, List<String>> tipos = Map.of(
            "valor", List.of(
                CondicionComisionServicio.DIAS_ATRASO,
                CondicionComisionServicio.PLAZO_MINIMO,
                CondicionComisionServicio.PLAZO_MAXIMO,
                CondicionComisionServicio.MONTO_MINIMO,
                CondicionComisionServicio.MONTO_MAXIMO
            ),
            "texto", List.of(
                CondicionComisionServicio.TIPO_CLIENTE,
                CondicionComisionServicio.SEGMENTO
            )
        );
        return ResponseEntity.ok(tipos);
    }
}
