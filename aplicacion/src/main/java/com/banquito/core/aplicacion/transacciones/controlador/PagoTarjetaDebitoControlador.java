package com.banquito.core.aplicacion.transacciones.controlador;

import com.banquito.core.aplicacion.transacciones.modelo.PagoTarjetaDebito;
import com.banquito.core.aplicacion.transacciones.servicio.PagoTarjetaDebitoServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos-tarjeta-debito")
public class PagoTarjetaDebitoControlador {

    private final PagoTarjetaDebitoServicio pagoServicio;

    public PagoTarjetaDebitoControlador(PagoTarjetaDebitoServicio pagoServicio) {
        this.pagoServicio = pagoServicio;
    }

    @GetMapping
    public ResponseEntity<List<PagoTarjetaDebito>> getAllPagos() {
        List<PagoTarjetaDebito> pagos = pagoServicio.findAll();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoTarjetaDebito> getPagoById(@PathVariable Integer id) {
        PagoTarjetaDebito pago = pagoServicio.findById(id);
        return ResponseEntity.ok(pago);
    }

    @PostMapping
    public ResponseEntity<PagoTarjetaDebito> createPago(@RequestBody PagoTarjetaDebito pago) {
        PagoTarjetaDebito nuevoPago = pagoServicio.create(pago);
        return ResponseEntity.ok(nuevoPago);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Integer id) {
        pagoServicio.delete(id);
        return ResponseEntity.noContent().build();
    }
}
