package com.banquito.core.aplicacion.transacciones.controlador;

import com.banquito.core.aplicacion.transacciones.modelo.Cheque;
import com.banquito.core.aplicacion.transacciones.servicio.ChequeServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cheques")
public class ChequeControlador {

    private final ChequeServicio chequeServicio;

    public ChequeControlador(ChequeServicio chequeServicio) {
        this.chequeServicio = chequeServicio;
    }

    @GetMapping
    public ResponseEntity<List<Cheque>> getAllCheques() {
        List<Cheque> cheques = chequeServicio.findAll();
        return ResponseEntity.ok(cheques);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cheque> getChequeById(@PathVariable Integer id) {
        Cheque cheque = chequeServicio.findById(id);
        return ResponseEntity.ok(cheque);
    }

    @PostMapping
    public ResponseEntity<Cheque> createCheque(@RequestBody Cheque cheque) {
        Cheque nuevoCheque = chequeServicio.create(cheque);
        return ResponseEntity.ok(nuevoCheque);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheque(@PathVariable Integer id) {
        chequeServicio.delete(id);
        return ResponseEntity.noContent().build();
    }
}
