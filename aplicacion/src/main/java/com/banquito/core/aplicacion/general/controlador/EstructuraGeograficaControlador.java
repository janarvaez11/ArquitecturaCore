package com.banquito.core.aplicacion.general.controlador;

import com.banquito.core.aplicacion.general.modelo.EstructuraGeografica;
import com.banquito.core.aplicacion.general.modelo.EstructuraGeograficaId;
import com.banquito.core.aplicacion.general.servicio.EstructuraGeograficaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estructuras-geograficas")
public class EstructuraGeograficaControlador {

    private final EstructuraGeograficaServicio servicio;

    public EstructuraGeograficaControlador(EstructuraGeograficaServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/{paisId}/{codigoNivel}")
    public ResponseEntity<EstructuraGeografica> obtenerPorId(
            @PathVariable String paisId,
            @PathVariable Integer codigoNivel) {
        EstructuraGeograficaId id = new EstructuraGeograficaId();
        id.setPaisId(paisId);
        id.setCodigoNivel(codigoNivel);
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EstructuraGeografica>> listarTodas() {
        return ResponseEntity.ok(servicio.listarTodas());
    }

    @GetMapping("/pais/{paisId}")
    public ResponseEntity<List<EstructuraGeografica>> buscarPorPais(@PathVariable String paisId) {
        return ResponseEntity.ok(servicio.buscarPorPais(paisId));
    }

    @GetMapping("/nivel/{codigoNivel}")
    public ResponseEntity<List<EstructuraGeografica>> buscarPorNivel(@PathVariable Integer codigoNivel) {
        return ResponseEntity.ok(servicio.buscarPorNivel(codigoNivel));
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody EstructuraGeografica estructura) {
        servicio.crear(estructura);
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<Void> actualizar(@RequestBody EstructuraGeografica estructura) {
        servicio.actualizar(estructura);
        return ResponseEntity.noContent().build();
    }
}
