package com.banquito.core.aplicacion.cuentas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.TasaInteresNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.TasaInteres;
import com.banquito.core.aplicacion.cuentas.repositorio.TasaInteresRepositorio;

import jakarta.transaction.Transactional;

@Service
public class TasaInteresServicio {

    public static final String ESTADO_VIGENTE = "VIGENTE";

    private final TasaInteresRepositorio tasaInteresRepositorio;

    public TasaInteresServicio(TasaInteresRepositorio tasaInteresRepositorio) {
        this.tasaInteresRepositorio = tasaInteresRepositorio;
    }

    public Page<TasaInteres> listarTodos(Pageable pageable) {
        return this.tasaInteresRepositorio.findAll(pageable);
    }

    public TasaInteres obtenerPorId(Integer id) {
        Optional<TasaInteres> tasaInteresOptional = this.tasaInteresRepositorio.findById(id);
        if (tasaInteresOptional.isPresent()) {
            return tasaInteresOptional.get();
        } else {
            throw new TasaInteresNoEncontradaExcepcion("TasaInteres", "No se encontró la tasa de interés con ID: " + id);
        }
    }

    @Transactional
    public TasaInteres crear(TasaInteres tasaInteres) {
        try {
            return this.tasaInteresRepositorio.save(tasaInteres);
        } catch (Exception e) {
            throw new CrearEntidadExcepcion("TasaInteres", "Error al crear la tasa de interés. Error: " + e.getMessage());
        }
    }

    @Transactional
    public TasaInteres actualizar(TasaInteres tasaInteres) {
        try {
            Optional<TasaInteres> tasaInteresOptional = this.tasaInteresRepositorio.findById(tasaInteres.getIdTasaInteres());
            if (tasaInteresOptional.isPresent()) {
                TasaInteres tasaInteresDB = tasaInteresOptional.get();
                tasaInteresDB.setBaseCalculo(tasaInteres.getBaseCalculo());
                tasaInteresDB.setMetodoCalculo(tasaInteres.getMetodoCalculo());
                tasaInteresDB.setEstado(tasaInteres.getEstado());
                tasaInteresDB.setFrecuenciaCapitalizacion(tasaInteres.getFrecuenciaCapitalizacion());
                tasaInteresDB.setFechaInicioVigencia(tasaInteres.getFechaInicioVigencia());
                return this.tasaInteresRepositorio.save(tasaInteresDB);
            } else {
                throw new TasaInteresNoEncontradaExcepcion("TasaInteres", "No se encontró la tasa de interés con ID: " + tasaInteres.getIdTasaInteres());
            }
        } catch (Exception e) {
            throw new ActualizarEntidadExcepcion("TasaInteres", "Error al actualizar la tasa de interés. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        try {
            Optional<TasaInteres> tasaInteresOptional = this.tasaInteresRepositorio.findById(id);
            if (tasaInteresOptional.isPresent()) {
                this.tasaInteresRepositorio.delete(tasaInteresOptional.get());
            } else {
                throw new TasaInteresNoEncontradaExcepcion("TasaInteres", "No se encontró la tasa de interés con ID: " + id);
            }
        } catch (Exception e) {
            throw new EliminarEntidadExcepcion("TasaInteres", "Error al eliminar la tasa de interés. Error: " + e.getMessage());
        }
    }

    public List<TasaInteres> buscarPorTipoCuenta(Integer idTipoCuenta) {
        return this.tasaInteresRepositorio.findByTipoCuentaId(idTipoCuenta);
    }

    public List<TasaInteres> buscarPorRangoSaldo(Double saldoMinimo, Double saldoMaximo) {
        return this.tasaInteresRepositorio.findByRangoSaldo(saldoMinimo, saldoMaximo);
    }

    public List<TasaInteres> buscarTasasVigentes() {
        return this.tasaInteresRepositorio.findByEstado(ESTADO_VIGENTE);
    }
}
