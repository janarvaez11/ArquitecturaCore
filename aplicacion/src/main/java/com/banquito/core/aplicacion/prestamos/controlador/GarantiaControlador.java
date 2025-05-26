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

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.GarantiaNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.Garantia;
import com.banquito.core.aplicacion.prestamos.servicio.GarantiaServicio;

@RestController
@RequestMapping("/api/v1/garantias")
public class GarantiaControlador {

    private final GarantiaServicio servicio;

    public GarantiaControlador(GarantiaServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Garantia> findById(@PathVariable Integer id) {
        try {
            Garantia garantia = servicio.findById(id);
            return ResponseEntity.ok(garantia);
        } catch (GarantiaNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tipo/{tipoGarantia}")
    public ResponseEntity<List<Garantia>> findByTipoGarantia(@PathVariable String tipoGarantia) {
        try {
            List<Garantia> garantias = servicio.findByTipoGarantia(tipoGarantia);
            return ResponseEntity.ok(garantias);
        } catch (GarantiaNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Garantia garantia) {
        try {
            servicio.create(garantia);
            return ResponseEntity.ok().build();
        } catch (CrearEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody Garantia garantia) {
        try {
            garantia.setId(id);
            servicio.update(garantia);
            return ResponseEntity.ok().build();
        } catch (ActualizarEntidadExcepcion | GarantiaNoEncontradoExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            servicio.delete(id);
            return ResponseEntity.ok().build();
        } catch (EliminarEntidadExcepcion | GarantiaNoEncontradoExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    
} 