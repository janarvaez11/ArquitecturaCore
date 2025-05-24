package com.banquito.core.aplicacion.general.controlador;

import com.banquito.core.aplicacion.general.modelo.Sucursal;
import com.banquito.core.aplicacion.general.servicio.SucursalServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalControlador {

    private final SucursalServicio servicio;

    public SucursalControlador(SucursalServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Sucursal> obtenerPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(servicio.obtenerPorCodigo(codigo));
    }

    @GetMapping
    public ResponseEntity<List<Sucursal>> listarTodas() {
        return ResponseEntity.ok(servicio.listarTodas());
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Sucursal>> listarActivas() {
        return ResponseEntity.ok(servicio.listarActivas());
    }

    @GetMapping("/locacion/{idLocacion}")
    public ResponseEntity<List<Sucursal>> buscarPorLocacion(@PathVariable Integer idLocacion) {
        return ResponseEntity.ok(servicio.buscarPorLocacion(idLocacion));
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody Sucursal sucursal) {
        servicio.crearSucursal(sucursal);
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<Void> actualizar(@RequestBody Sucursal sucursal) {
        servicio.actualizarSucursal(sucursal);
        return ResponseEntity.noContent().build();
    }
}
