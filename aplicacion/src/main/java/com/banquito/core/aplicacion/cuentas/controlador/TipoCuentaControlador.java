package com.banquito.core.aplicacion.cuentas.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.core.aplicacion.cuentas.modelo.TipoCuenta;
import com.banquito.core.aplicacion.cuentas.servicio.TipoCuentaServicio;
import com.banquito.core.aplicacion.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;

@RestController
@RequestMapping("/api/tipos-cuenta")
@CrossOrigin(maxAge = 3600)
public class TipoCuentaControlador {

    private final TipoCuentaServicio tipoCuentaServicio;

    public TipoCuentaControlador(TipoCuentaServicio tipoCuentaServicio) {
        this.tipoCuentaServicio = tipoCuentaServicio;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listarTodos() {
        try {
            List<Map<String, Object>> tipos = tipoCuentaServicio.listarTodos();
            return ResponseEntity.ok(tipos);
        } catch (EntidadNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<TipoCuenta>> listarTodosPaginado(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        try {
            Page<TipoCuenta> tipos = tipoCuentaServicio.listarTodosPaginado(PageRequest.of(pagina, tamanio));
            return ResponseEntity.ok(tipos);
        } catch (EntidadNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable Integer id) {
        try {
            Map<String, Object> tipo = tipoCuentaServicio.buscarPorIdMapeado(id);
            return ResponseEntity.ok(tipo);
        } catch (EntidadNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TipoCuenta> crear(@RequestBody TipoCuenta tipoCuenta) {
        try {
            TipoCuenta nuevoTipo = tipoCuentaServicio.crear(tipoCuenta);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTipo);
        } catch (CrearEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCuenta> actualizar(
            @PathVariable Integer id, 
            @RequestBody TipoCuenta tipoCuenta) {
        try {
            TipoCuenta tipoActualizado = tipoCuentaServicio.actualizar(id, tipoCuenta);
            return ResponseEntity.ok(tipoActualizado);
        } catch (EntidadNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        } catch (ActualizarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            tipoCuentaServicio.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        } catch (EliminarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Map<String, Object>>> buscarPorNombre(@PathVariable String nombre) {
        try {
            List<Map<String, Object>> tipos = tipoCuentaServicio.buscarPorNombre(nombre);
            return ResponseEntity.ok(tipos);
        } catch (EntidadNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/moneda/{idMoneda}")
    public ResponseEntity<List<Map<String, Object>>> buscarPorMoneda(@PathVariable String idMoneda) {
        try {
            List<Map<String, Object>> tipos = tipoCuentaServicio.buscarPorMoneda(idMoneda);
            return ResponseEntity.ok(tipos);
        } catch (EntidadNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tipo-cliente/{tipoCliente}")
    public ResponseEntity<List<Map<String, Object>>> buscarPorTipoCliente(@PathVariable String tipoCliente) {
        try {
            List<Map<String, Object>> tipos = tipoCuentaServicio.buscarPorTipoCliente(tipoCliente);
            return ResponseEntity.ok(tipos);
        } catch (EntidadNoEncontradaExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }
} 