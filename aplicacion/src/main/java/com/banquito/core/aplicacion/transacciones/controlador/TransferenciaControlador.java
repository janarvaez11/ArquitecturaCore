package com.banquito.core.aplicacion.transacciones.controlador;

import com.banquito.core.aplicacion.transacciones.modelo.Transferencia;
import com.banquito.core.aplicacion.transacciones.modelo.TransferenciaId;
import com.banquito.core.aplicacion.transacciones.servicio.TransferenciaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaControlador {

    private final TransferenciaServicio transferenciaServicio;

    public TransferenciaControlador(TransferenciaServicio transferenciaServicio) {
        this.transferenciaServicio = transferenciaServicio;
    }

    @GetMapping
    public ResponseEntity<List<Transferencia>> getAllTransferencias() {
        List<Transferencia> transferencias = transferenciaServicio.findAll();
        return ResponseEntity.ok(transferencias);
    }

    @GetMapping("/id")
    public ResponseEntity<Transferencia> getTransferenciaById(
            @RequestParam Integer transferenciaId,
            @RequestParam Integer tipoTransaccionId,
            @RequestParam Integer transaccionId,
            @RequestParam Integer cuentaOrigenId,
            @RequestParam Integer cuentaDestinoId) {
        TransferenciaId id = new TransferenciaId(transferenciaId, tipoTransaccionId, transaccionId, cuentaOrigenId, cuentaDestinoId);
        Transferencia transferencia = transferenciaServicio.findById(id);
        return ResponseEntity.ok(transferencia);
    }

    @PostMapping
    public ResponseEntity<Transferencia> createTransferencia(@RequestBody Transferencia transferencia) {
        Transferencia nuevaTransferencia = transferenciaServicio.create(transferencia);
        return ResponseEntity.ok(nuevaTransferencia);
    }

    @PutMapping("/id")
    public ResponseEntity<Transferencia> updateTransferencia(
            @RequestParam Integer transferenciaId,
            @RequestParam Integer tipoTransaccionId,
            @RequestParam Integer transaccionId,
            @RequestParam Integer cuentaOrigenId,
            @RequestParam Integer cuentaDestinoId,
            @RequestBody Transferencia transferencia) {
        TransferenciaId id = new TransferenciaId(transferenciaId, tipoTransaccionId, transaccionId, cuentaOrigenId, cuentaDestinoId);
        Transferencia transferenciaActualizada = transferenciaServicio.update(id, transferencia);
        return ResponseEntity.ok(transferenciaActualizada);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteTransferencia(
            @RequestParam Integer transferenciaId,
            @RequestParam Integer tipoTransaccionId,
            @RequestParam Integer transaccionId,
            @RequestParam Integer cuentaOrigenId,
            @RequestParam Integer cuentaDestinoId) {
        TransferenciaId id = new TransferenciaId(transferenciaId, tipoTransaccionId, transaccionId, cuentaOrigenId, cuentaDestinoId);
        transferenciaServicio.delete(id);
        return ResponseEntity.noContent().build();
    }
}
