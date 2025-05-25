package com.banquito.core.aplicacion.cuentas.controlador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.ServicioAsociadoNoEncontradoExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.ServicioAsociado;
import com.banquito.core.aplicacion.cuentas.servicio.ServicioAsociadoServicio;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/serviciosAsociados")
public class ServicioAsociadoControlador {

    private final ServicioAsociadoServicio servicioAsociadoServicio;

    public ServicioAsociadoControlador(ServicioAsociadoServicio servicioAsociadoServicio) {
        this.servicioAsociadoServicio = servicioAsociadoServicio;
    }

    @GetMapping
    public ResponseEntity<List<ServicioAsociado>> obtenerTodos() {
        return ResponseEntity.ok(servicioAsociadoServicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioAsociado> obtenerPorId(@PathVariable Integer id) {
        try {
            ServicioAsociado servicioAsociado = servicioAsociadoServicio.findById(id);
            return ResponseEntity.ok(servicioAsociado);
        } catch (ServicioAsociadoNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody ServicioAsociado servicioAsociado) {
        try {
            servicioAsociadoServicio.create(servicioAsociado);
            return ResponseEntity.ok().build();
        } catch (CrearEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id, @RequestBody ServicioAsociado servicioAsociado) {
        try {
            servicioAsociado.setIdServicio(id);
            servicioAsociadoServicio.update(servicioAsociado);
            return ResponseEntity.ok().build();
        } catch (ActualizarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            servicioAsociadoServicio.delete(id);
            return ResponseEntity.ok().build();
        } catch (EliminarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }
}