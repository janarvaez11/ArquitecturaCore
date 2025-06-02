package com.banquito.core.aplicacion.prestamos.controlador;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.BusquedaExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.ComisionPrestamoNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.ComisionPrestamo;
import com.banquito.core.aplicacion.prestamos.modelo.CondicionComision;
import com.banquito.core.aplicacion.prestamos.modelo.PrestamoComisionCargo;
import com.banquito.core.aplicacion.prestamos.modelo.PrestamoComisionCargoId;
import com.banquito.core.aplicacion.prestamos.servicio.ComisionPrestamoServicio;
import com.banquito.core.aplicacion.prestamos.servicio.CondicionComisionServicio;
import com.banquito.core.aplicacion.prestamos.servicio.PrestamoComisionCargoServicio;
import com.banquito.core.aplicacion.prestamos.modelo.ExencionesPrestamo;
import com.banquito.core.aplicacion.prestamos.servicio.ExencionesServicio;

// DTO para crear exenciones
class CrearExencionDTO {
    private Integer idComisionPrestamo;
    private String tipoExencion;
    private String nombre;
    private String descripcion;

    // Getters y Setters
    public Integer getIdComisionPrestamo() {
        return idComisionPrestamo;
    }

    public void setIdComisionPrestamo(Integer idComisionPrestamo) {
        this.idComisionPrestamo = idComisionPrestamo;
    }

    public String getTipoExencion() {
        return tipoExencion;
    }

    public void setTipoExencion(String tipoExencion) {
        this.tipoExencion = tipoExencion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/prestamos/comisiones")
public class ComisionPrestamoControlador {

    private final ComisionPrestamoServicio comisionPrestamoServicio;
    private final CondicionComisionServicio condicionComisionServicio;
    private final PrestamoComisionCargoServicio prestamoComisionCargoServicio;
    private final ExencionesServicio exencionesServicio;

    public ComisionPrestamoControlador(
            ComisionPrestamoServicio comisionPrestamoServicio,
            CondicionComisionServicio condicionComisionServicio,
            PrestamoComisionCargoServicio prestamoComisionCargoServicio,
            ExencionesServicio exencionesServicio) {
        this.comisionPrestamoServicio = comisionPrestamoServicio;
        this.condicionComisionServicio = condicionComisionServicio;
        this.prestamoComisionCargoServicio = prestamoComisionCargoServicio;
        this.exencionesServicio = exencionesServicio;
    }

    // Endpoints para Comisiones
    @GetMapping
    public ResponseEntity<List<ComisionPrestamo>> listarComisiones() {
        return ResponseEntity.ok(comisionPrestamoServicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComisionPrestamo> obtenerComision(@PathVariable Integer id) {
        try {
            ComisionPrestamo comision = comisionPrestamoServicio.findById(id);
            return ResponseEntity.ok(comision);
        } catch (ComisionPrestamoNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ComisionPrestamo> crearComision(@RequestBody ComisionPrestamo comision) {
        try {
            comisionPrestamoServicio.create(comision);
            return ResponseEntity.ok(comision);
        } catch (CrearEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComisionPrestamo> actualizarComision(
            @PathVariable Integer id,
            @RequestBody ComisionPrestamo comision) {
        try {
            comision.setId(id);
            comisionPrestamoServicio.update(comision);
            return ResponseEntity.ok(comision);
        } catch (ActualizarEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoints para Condiciones de Comisión
    @GetMapping("/condiciones")
    public ResponseEntity<List<CondicionComision>> listarCondiciones() {
        return ResponseEntity.ok(condicionComisionServicio.findAll());
    }

    @GetMapping("/condiciones/{id}")
    public ResponseEntity<CondicionComision> obtenerCondicion(@PathVariable Integer id) {
        return ResponseEntity.ok(condicionComisionServicio.findById(id));
    }

    @GetMapping("/condiciones/tipo/{tipoCondicion}")
    public ResponseEntity<List<CondicionComision>> listarCondicionesPorTipo(
            @PathVariable String tipoCondicion) {
        return ResponseEntity.ok(condicionComisionServicio.findByTipoCondicion(tipoCondicion));
    }

    @PostMapping("/condiciones")
    public ResponseEntity<CondicionComision> crearCondicion(@RequestBody CondicionComision condicion) {
        condicionComisionServicio.create(condicion);
        return ResponseEntity.ok(condicion);
    }

    @PutMapping("/condiciones/{id}")
    public ResponseEntity<CondicionComision> actualizarCondicion(
            @PathVariable Integer id,
            @RequestBody CondicionComision condicion) {
        condicion.setId(id);
        condicionComisionServicio.update(condicion);
        return ResponseEntity.ok(condicion);
    }

    @DeleteMapping("/condiciones/{id}")
    public ResponseEntity<Void> eliminarCondicion(@PathVariable Integer id) {
        condicionComisionServicio.delete(id);
        return ResponseEntity.ok().build();
    }

    // Endpoints para Asignación de Comisiones a Préstamos
    @GetMapping("/prestamo/{idPrestamo}")
    public ResponseEntity<List<Map<String, Object>>> obtenerComisionesPorPrestamo(
            @PathVariable Integer idPrestamo) {
        return ResponseEntity.ok(prestamoComisionCargoServicio.findComisionesByPrestamo(idPrestamo));
    }

    @PostMapping("/prestamo/{idPrestamo}/comision/{idComision}")
    public ResponseEntity<Void> asignarComisionAPrestamo(
            @PathVariable Integer idPrestamo,
            @PathVariable Integer idComision) {
        try {
            // Crear el ID compuesto
            PrestamoComisionCargoId id = new PrestamoComisionCargoId();
            id.setIdPrestamo(idPrestamo);
            id.setIdComisionPrestamo(idComision);
            
            // Crear la entidad con fecha actual
            PrestamoComisionCargo cargo = new PrestamoComisionCargo();
            cargo.setId(id);
            cargo.setFechaAsignacion(LocalDateTime.now());
            
            prestamoComisionCargoServicio.create(cargo);
            return ResponseEntity.ok().build();
        } catch (CrearEntidadExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/exenciones/prestamo/{idPrestamo}")
    public ResponseEntity<List<ExencionesPrestamo>> obtenerExencionesPorPrestamo(@PathVariable Integer idPrestamo) {
        try {
            List<ExencionesPrestamo> exenciones = exencionesServicio.findByPrestamo(idPrestamo);
            return ResponseEntity.ok(exenciones);
        } catch (BusquedaExcepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/exenciones")
    public ResponseEntity<ExencionesPrestamo> crearExencion(@RequestBody CrearExencionDTO dto) {
        try {
            // Buscar la comisión por ID
            ComisionPrestamo comision = comisionPrestamoServicio.findById(dto.getIdComisionPrestamo());
            
            // Crear la exención
            ExencionesPrestamo exencion = new ExencionesPrestamo();
            exencion.setIdComisionPrestamo(comision);
            exencion.setTipoExencion(dto.getTipoExencion());
            exencion.setNombre(dto.getNombre());
            exencion.setDescripcion(dto.getDescripcion());
            
            exencionesServicio.create(exencion);
            return ResponseEntity.ok(exencion);
        } catch (CrearEntidadExcepcion | ComisionPrestamoNoEncontradoExcepcion e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
