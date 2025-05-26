package com.banquito.core.aplicacion.cuentas.controlador;

import com.banquito.core.aplicacion.cuentas.modelo.TasaInteres;
import com.banquito.core.aplicacion.cuentas.servicio.TasaInteresServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasas-interes")
public class TasaInteresControlador {

    @Autowired
    private TasaInteresServicio tasaInteresServicio;

    @GetMapping
    public ResponseEntity<Page<TasaInteres>> listarTasasInteres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(tasaInteresServicio.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TasaInteres> obtenerTasaInteres(@PathVariable Integer id) {
        return ResponseEntity.ok(tasaInteresServicio.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<TasaInteres> crearTasaInteres(@RequestBody TasaInteres tasaInteres) {
        return ResponseEntity.ok(tasaInteresServicio.crear(tasaInteres));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TasaInteres> actualizarTasaInteres(
            @PathVariable Integer id,
            @RequestBody TasaInteres tasaInteres) {
        tasaInteres.setIdTasaInteres(id);
        return ResponseEntity.ok(tasaInteresServicio.actualizar(tasaInteres));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTasaInteres(@PathVariable Integer id) {
        tasaInteresServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo-cuenta/{idTipoCuenta}")
    public ResponseEntity<List<TasaInteres>> buscarPorTipoCuenta(@PathVariable Integer idTipoCuenta) {
        return ResponseEntity.ok(tasaInteresServicio.buscarPorTipoCuenta(idTipoCuenta));
    }

    @GetMapping("/rango-saldo")
    public ResponseEntity<List<TasaInteres>> buscarPorRangoSaldo(
            @RequestParam Double saldoMinimo,
            @RequestParam Double saldoMaximo) {
        return ResponseEntity.ok(tasaInteresServicio.buscarPorRangoSaldo(saldoMinimo, saldoMaximo));
    }

    @GetMapping("/vigentes")
    public ResponseEntity<List<TasaInteres>> buscarTasasVigentes() {
        return ResponseEntity.ok(tasaInteresServicio.buscarTasasVigentes());
    }
} 