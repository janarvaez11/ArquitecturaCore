package com.banquito.core.aplicacion.general.controlador;

import com.banquito.core.aplicacion.general.modelo.Moneda;
import com.banquito.core.aplicacion.general.servicio.MonedaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monedas")
public class MonedaControlador {

    private final MonedaServicio monedaServicio;

    public MonedaControlador(MonedaServicio monedaServicio) {
        this.monedaServicio = monedaServicio;
    }

    @PostMapping
    public ResponseEntity<Moneda> crearMoneda(@RequestBody Moneda moneda) {
        monedaServicio.crearMoneda(moneda);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pais/{idPais}")
    public ResponseEntity<Moneda> crearMonedaPorPais(@RequestBody Moneda moneda, @PathVariable String idPais) {
        Pais pais = new Pais(idPais);
        monedaServicio.crearMonedaPorPais(moneda, pais);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{idMoneda}/{idEntidadBancaria}")
    public ResponseEntity<Moneda> asignarMonedaAEntidadBancaria(
            @PathVariable String idMoneda,
            @PathVariable Integer idEntidadBancaria) {
        monedaServicio.asignarMonedaAEntidadBancaria(idMoneda, idEntidadBancaria);
        return ResponseEntity.noContent().build();
    }
}
