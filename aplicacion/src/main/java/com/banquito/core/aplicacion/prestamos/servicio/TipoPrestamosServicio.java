package com.banquito.core.aplicacion.prestamos.servicio;

import java.util.Optional;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.general.modelo.Moneda;
import com.banquito.core.aplicacion.general.repositorio.MonedaRepositorio;
import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.TipoPrestamoNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.TipoPrestamo;
import com.banquito.core.aplicacion.prestamos.repositorio.TipoPrestamoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class TipoPrestamosServicio {
    
    private final TipoPrestamoRepositorio repositorio;
    private final MonedaRepositorio monedaRepositorio;

    public TipoPrestamosServicio(TipoPrestamoRepositorio repositorio, MonedaRepositorio monedaRepositorio) {
        this.repositorio = repositorio;
        this.monedaRepositorio = monedaRepositorio;
    }

    public TipoPrestamo findById(Integer id) {
        Optional<TipoPrestamo> tipoOptional = this.repositorio.findById(id);
        if (tipoOptional.isPresent()) {
            return tipoOptional.get();
        } else {
            throw new TipoPrestamoNoEncontradoExcepcion("Tipos Prestamos","Tipo de préstamo no encontrado con ID: " + id);
        }
    }

    @Transactional
    public void create (TipoPrestamo tipoPrestamo) {
        try {
            // Verificar si la moneda existe
            if (tipoPrestamo.getMoneda() != null && tipoPrestamo.getMoneda().getId() != null) {
                Optional<Moneda> monedaOptional = monedaRepositorio.findById(tipoPrestamo.getMoneda().getId());
                if (monedaOptional.isPresent()) {
                    tipoPrestamo.setMoneda(monedaOptional.get());
                } else {
                    throw new CrearEntidadExcepcion("Tipo Prestamos", "La moneda con ID " + tipoPrestamo.getMoneda().getId() + " no existe");
                }
            } else {
                throw new CrearEntidadExcepcion("Tipo Prestamos", "Debe especificar una moneda válida");
            }
            tipoPrestamo.setFechaCreacion(LocalDate.now());
            tipoPrestamo.setFechaModificacion(LocalDate.now());
            this.repositorio.save(tipoPrestamo);
        } catch (CrearEntidadExcepcion e) {
            throw e;
        } catch (Exception rte) {
            throw new CrearEntidadExcepcion("Tipo Prestamos", "Error al crear el Tipo de prestamo. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void update(TipoPrestamo tipoPrestamo) {
        try{
            Optional<TipoPrestamo> tipoOptional = this.repositorio.findById(tipoPrestamo.getIdTipoPrestamo());

            if (tipoOptional.isPresent()) {
                // Verificar si la moneda existe
                if (tipoPrestamo.getMoneda() != null && tipoPrestamo.getMoneda().getId() != null) {
                    Optional<Moneda> monedaOptional = monedaRepositorio.findById(tipoPrestamo.getMoneda().getId());
                    if (monedaOptional.isPresent()) {
                        tipoPrestamo.setMoneda(monedaOptional.get());
                    } else {
                        throw new ActualizarEntidadExcepcion("Tipo Prestamos", "La moneda con ID " + tipoPrestamo.getMoneda().getId() + " no existe");
                    }
                } else {
                    throw new ActualizarEntidadExcepcion("Tipo Prestamos", "Debe especificar una moneda válida");
                }

                TipoPrestamo tipoPrestamoDb = tipoOptional.get();
                tipoPrestamoDb.setNombre(tipoPrestamo.getNombre());
                tipoPrestamoDb.setDescripcion(tipoPrestamo.getDescripcion());
                tipoPrestamoDb.setMontoMinimo(tipoPrestamo.getMontoMinimo());
                tipoPrestamoDb.setMontoMaximo(tipoPrestamo.getMontoMaximo());
                tipoPrestamoDb.setPlazoMinimo(tipoPrestamo.getPlazoMinimo());
                tipoPrestamoDb.setPlazoMaximo(tipoPrestamo.getPlazoMaximo());
                tipoPrestamoDb.setRequisitos(tipoPrestamo.getRequisitos());
                tipoPrestamoDb.setTipoCliente(tipoPrestamo.getTipoCliente());
                tipoPrestamoDb.setEstado(tipoPrestamo.getEstado());
                tipoPrestamoDb.setFechaModificacion(LocalDate.now());
                tipoPrestamoDb.setMoneda(tipoPrestamo.getMoneda());

                this.repositorio.save(tipoPrestamoDb);
            } else {
                throw new TipoPrestamoNoEncontradoExcepcion("Tipo prestamo", "Error al actualizar el Tipo de prestamo. No se encontró el tipo de prestamo con ID: " + tipoPrestamo.getIdTipoPrestamo());
            }
        } catch (ActualizarEntidadExcepcion e) {
            throw e;
        } catch (Exception rte) {
            throw new ActualizarEntidadExcepcion("Tipo Prestamos", "Error al actualizar el Tipo de prestamo. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<TipoPrestamo> tipoOptional = this.repositorio.findById(id);
            if (tipoOptional.isPresent()) {
                this.repositorio.delete(tipoOptional.get());
            } else {
                throw new TipoPrestamoNoEncontradoExcepcion("Tipo prestamo", "Error al eliminar el Tipo de prestamo. No se encontró el tipo de prestamo con ID: " + id);
            }
        } catch (Exception rte) {
            throw new EliminarEntidadExcepcion("Tipo Prestamos", "Error al eliminar el Tipo de prestamo. Texto del error: "+rte.getMessage());
        }
    }

}
