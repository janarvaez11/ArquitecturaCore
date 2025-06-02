package com.banquito.core.aplicacion.transacciones.controlador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.aplicacion.transacciones.modelo.EstadoTransaccion;
import com.banquito.core.aplicacion.transacciones.servicio.EstadoTransaccionServicio;

@RestController
@RequestMapping("/api/estados-transaccion")
public class EstadoTransaccionControlador {

    private final EstadoTransaccionServicio estadoServicio;

    public EstadoTransaccionControlador(EstadoTransaccionServicio estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    @GetMapping
    public ResponseEntity<List<EstadoTransaccion>> getAllEstados() {
        List<EstadoTransaccion> estados = estadoServicio.findAll();
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<EstadoTransaccion> getEstadoById(@PathVariable String estadoId) {
        EstadoTransaccion estado = estadoServicio.findById(estadoId);
        return ResponseEntity.ok(estado);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<EstadoTransaccion>> getEstadosDisponibles() {
        List<EstadoTransaccion> estadosDisponibles = estadoServicio.findEstadosDisponibles();
        return ResponseEntity.ok(estadosDisponibles);
    }

    @GetMapping("/finales")
    public ResponseEntity<List<EstadoTransaccion>> getEstadosFinales() {
        List<EstadoTransaccion> estadosFinales = estadoServicio.findEstadosFinales();
        return ResponseEntity.ok(estadosFinales);
    }

    @GetMapping("/{estadoId}/validar")
    public ResponseEntity<Boolean> validarEstado(@PathVariable String estadoId) {
        boolean esValido = estadoServicio.esEstadoValido(estadoId);
        return ResponseEntity.ok(esValido);
    }

    @GetMapping("/{estadoId}/es-final")
    public ResponseEntity<Boolean> esEstadoFinal(@PathVariable String estadoId) {
        boolean esFinal = estadoServicio.esEstadoFinal(estadoId);
        return ResponseEntity.ok(esFinal);
    }

    @GetMapping("/{estadoActual}/puede-transicionar")
    public ResponseEntity<Boolean> puedeTransicionarA(
            @PathVariable String estadoActual,
            @RequestParam String nuevoEstado) {
        boolean puedeTransicionar = estadoServicio.puedeTransicionarA(estadoActual, nuevoEstado);
        return ResponseEntity.ok(puedeTransicionar);
    }

    @GetMapping("/{estadoActual}/siguiente-recomendado")
    public ResponseEntity<String> obtenerEstadoSiguienteRecomendado(@PathVariable String estadoActual) {
        String estadoSiguiente = estadoServicio.obtenerEstadoSiguienteRecomendado(estadoActual);
        return ResponseEntity.ok(estadoSiguiente);
    }

    @PostMapping
    public ResponseEntity<EstadoTransaccion> createEstado(@RequestBody EstadoTransaccion estado) {
        EstadoTransaccion nuevoEstado = estadoServicio.create(estado);
        return ResponseEntity.ok(nuevoEstado);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<EstadoTransaccion> updateEstado(
            @PathVariable String estadoId,
            @RequestBody EstadoTransaccion estado) {
        EstadoTransaccion estadoActualizado = estadoServicio.update(estadoId, estado);
        return ResponseEntity.ok(estadoActualizado);
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<Void> deleteEstado(@PathVariable String estadoId) {
        estadoServicio.delete(estadoId);
        return ResponseEntity.noContent().build();
    }
}
