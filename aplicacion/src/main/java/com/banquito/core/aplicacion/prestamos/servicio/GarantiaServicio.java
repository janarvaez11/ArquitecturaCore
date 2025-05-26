package com.banquito.core.aplicacion.prestamos.servicio;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.BusquedaExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.Garantia;
import com.banquito.core.aplicacion.prestamos.repositorio.GarantiaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class GarantiaServicio {
    
    private final GarantiaRepositorio repositorio;
    private static final List<String> TIPOS_GARANTIA_PERMITIDOS = Arrays.asList(
        "PERSONAL",
        "HIPOTECARIA",
        "PRENDARIA"
    );

    public GarantiaServicio(GarantiaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Transactional
    public void create(Garantia garantia) {
        try {
            validarGarantia(garantia);
            this.repositorio.save(garantia);
        } catch (CrearEntidadExcepcion e) {
            throw e;
        } catch (Exception e) {
            throw new CrearEntidadExcepcion("Garantia", "Error al crear la garantía: " + e.getMessage());
        }
    }

    @Transactional
    public void update(Garantia garantia) {
        try {
            validarGarantia(garantia);
            Optional<Garantia> garantiaOpcional = this.repositorio.findById(garantia.getId());

            if (garantiaOpcional.isPresent()) {
                Garantia garantiaDb = garantiaOpcional.get();
                garantiaDb.setDescripcion(garantia.getDescripcion());
                garantiaDb.setValor(garantia.getValor());
                garantiaDb.setTipoGarantia(garantia.getTipoGarantia());

                this.repositorio.save(garantiaDb);
            } else {
                throw new BusquedaExcepcion("Garantia", 
                    "No se encontró la garantía con ID: " + garantia.getId());
            }
        } catch (BusquedaExcepcion e) {
            throw e;
        } catch (Exception e) {
            throw new ActualizarEntidadExcepcion("Garantia", "Error al actualizar la garantía: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<Garantia> garantiaOpcional = this.repositorio.findById(id);
            if (garantiaOpcional.isPresent()) {
                this.repositorio.delete(garantiaOpcional.get());
            } else {
                throw new BusquedaExcepcion("Garantia", "No se encontró la garantía con ID: " + id);
            }
        } catch (BusquedaExcepcion e) {
            throw e;
        } catch (Exception e) {
            throw new EliminarEntidadExcepcion("Garantia", "Error al eliminar la garantía: " + e.getMessage());
        }
    }

    public Garantia findById(Integer id) {
        try {
            Optional<Garantia> garantiaOpcional = this.repositorio.findById(id);
            if (garantiaOpcional.isPresent()) {
                return garantiaOpcional.get();
            } else {
                throw new BusquedaExcepcion("Garantia", "Garantia no encontrada con ID: " + id);
            }
        } catch (BusquedaExcepcion e) {
            throw new BusquedaExcepcion("Garantia", "Error al buscar la garantía: " + e.getMessage());
        }
    }

    public List<Garantia> findByTipoGarantia(String tipoGarantia) {
        try {
            if (!TIPOS_GARANTIA_PERMITIDOS.contains(tipoGarantia)) {
                throw new BusquedaExcepcion("Garantia", "Tipo de garantía no válido");
            }
            return this.repositorio.findByTipoGarantia(tipoGarantia);
        } catch (Exception e) {
            throw new BusquedaExcepcion("Garantia", "Error al buscar garantías por tipo: " + e.getMessage());
        }
    }

    private void validarGarantia(Garantia garantia) {
        if (garantia.getTipoGarantia() == null || garantia.getTipoGarantia().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("Garantia", "El tipo de garantía es obligatorio");
        }

        if (!TIPOS_GARANTIA_PERMITIDOS.contains(garantia.getTipoGarantia())) {
            throw new CrearEntidadExcepcion("Garantia", 
                "Tipo de garantía no válido. Debe ser uno de: " + String.join(", ", TIPOS_GARANTIA_PERMITIDOS));
        }

        if (garantia.getDescripcion() == null || garantia.getDescripcion().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("Garantia", "La descripción es obligatoria");
        }

        // Si es garantía personal, establecemos el valor como null
        if ("PERSONAL".equals(garantia.getTipoGarantia())) {
            garantia.setValor(null);
        }
    }
}
