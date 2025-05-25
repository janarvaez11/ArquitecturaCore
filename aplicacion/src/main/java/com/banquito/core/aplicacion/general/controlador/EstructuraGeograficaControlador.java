package com.banquito.core.aplicacion.general.controlador;

import com.banquito.core.aplicacion.general.modelo.EstructuraGeografica;
import com.banquito.core.aplicacion.general.modelo.EstructuraGeograficaId;
import com.banquito.core.aplicacion.general.servicio.EstructuraGeograficaServicio;
import org.springframework.http.HttpStatus;
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
        if (!"EC".equals(paisId)) {
            throw new IllegalArgumentException("Solo se permiten operaciones para Ecuador (ID: EC)");
        }
        return ResponseEntity.ok(servicio.buscarPorPais(paisId));
    }

    @GetMapping("/nivel/{codigoNivel}")
    public ResponseEntity<List<EstructuraGeografica>> buscarPorNivel(@PathVariable Integer codigoNivel) {
        if (codigoNivel < 1 || codigoNivel > 2) {
            throw new IllegalArgumentException("El nivel debe ser 1 (Provincia) o 2 (Cantón)");
        }
        return ResponseEntity.ok(servicio.buscarPorNivel(codigoNivel));
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody EstructuraGeografica estructura) {
        validarSoloEcuador(estructura.getId().getPaisId());
        servicio.crear(estructura);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping
    public ResponseEntity<Void> actualizar(@RequestBody EstructuraGeografica estructura) {
        validarSoloEcuador(estructura.getId().getPaisId());
        servicio.actualizar(estructura);
        return ResponseEntity.noContent().build();
    }


    private void validarSoloEcuador(String paisId) {
        if (!"EC".equals(paisId)) {
            throw new IllegalArgumentException("Solo se permite crear/modificar estructuras para Ecuador (ID: EC)");
        }
    }
}