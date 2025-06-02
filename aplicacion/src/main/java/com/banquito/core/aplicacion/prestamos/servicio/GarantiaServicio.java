package com.banquito.core.aplicacion.prestamos.servicio;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.BusquedaExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.GarantiaNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.Garantia;
import com.banquito.core.aplicacion.prestamos.modelo.TipoPrestamo;
import com.banquito.core.aplicacion.prestamos.repositorio.GarantiaRepositorio;
import com.banquito.core.aplicacion.prestamos.repositorio.TipoPrestamoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class GarantiaServicio {
    
    private final GarantiaRepositorio repositorio;
    private final TipoPrestamoRepositorio tipoPrestamoRepositorio;

    // Lista de tipos de garantía permitidos
    private static final List<String> TIPOS_GARANTIA_PERMITIDOS = Arrays.asList(
            "HIPOTECA",
            "PRENDARIA",
            "PERSONAL");

    // Lista de estados permitidos
    private static final List<String> ESTADOS_PERMITIDOS = Arrays.asList(
            "ACTIVA",
            "LIBERADA",
            "ANULADA");

    public GarantiaServicio(GarantiaRepositorio repositorio, TipoPrestamoRepositorio tipoPrestamoRepositorio) {
        this.repositorio = repositorio;
        this.tipoPrestamoRepositorio = tipoPrestamoRepositorio;
    }

    public Map<String, Object> findById(Integer id) {
        Optional<Garantia> garantiaOpcional = this.repositorio.findById(id);
        if (garantiaOpcional.isPresent()) {
            Garantia garantia = garantiaOpcional.get();
            Map<String, Object> resultado = new HashMap<>();
            
            // Campos de la tabla Garantia
            resultado.put("id", garantia.getId());
            resultado.put("descripcion", garantia.getDescripcion());
            resultado.put("tipoGarantia", garantia.getTipoGarantia());
            resultado.put("valor", garantia.getValor());
            resultado.put("estado", garantia.getEstado());
            
            // Campo nombre del TipoPrestamo
            resultado.put("tipoPrestamo", garantia.getTipoPrestamo().getNombre());
            
            return resultado;
        } else {
            throw new GarantiaNoEncontradoExcepcion("Garantia","Garantia no encontrado con ID: " + id);
        }
    }

    public List<Garantia> findByTipoGarantia(String tipoGarantia) {
        try {
            if (tipoGarantia == null || tipoGarantia.trim().isEmpty()) {
                throw new BusquedaExcepcion("Garantia", "El tipo de garantía no puede estar vacío");
            }
            if (!TIPOS_GARANTIA_PERMITIDOS.contains(tipoGarantia)) {
                throw new BusquedaExcepcion("Garantia",
                        "El tipo de garantía debe ser uno de los valores permitidos: " +
                                String.join(", ", TIPOS_GARANTIA_PERMITIDOS));
            }
            return this.repositorio.findByTipoGarantia(tipoGarantia);
        } catch (BusquedaExcepcion e) {
            throw e;
        } catch (Exception e) {
            throw new BusquedaExcepcion("Garantia",
                    "Error al buscar garantías por tipo: " + tipoGarantia, e);
        }
    }

    @Transactional
    public void create(Garantia garantia) {
        try {
            validarGarantia(garantia);
            garantia.setEstado("ACTIVA"); // Estado por defecto al crear
            this.repositorio.save(garantia);
        } catch (Exception rte) {
            throw new CrearEntidadExcepcion("Garantia", "Error al crear la Garantia. Texto del error: "+rte.getMessage());
        }
    }

    public List<Garantia> findByTipoPrestamo(Integer idTipoPrestamo) {
        try {
            if (idTipoPrestamo == null) {
                throw new BusquedaExcepcion("Garantia", "El idTipoPrestamo no puede ser nulo");
            }
            return this.repositorio.findByTipoPrestamo_IdTipoPrestamo(idTipoPrestamo);
        } catch (RuntimeException rte) {
            throw new BusquedaExcepcion("Garantia",
                "Error al buscar garantías por tipo de préstamo. Texto del error: " + rte.getMessage());
        }
    }

    @Transactional
    public void update(Garantia garantia) {
        try {
            validarGarantia(garantia);
            Optional<Garantia> garantiaOpcional = this.repositorio.findById(garantia.getId());

            if (garantiaOpcional.isPresent()) {
                Garantia garantiaDb = garantiaOpcional.get();
                
                // No permitir cambiar el tipo de préstamo
                if (!garantiaDb.getTipoPrestamo().getIdTipoPrestamo().equals(garantia.getTipoPrestamo().getIdTipoPrestamo())) {
                    throw new ActualizarEntidadExcepcion("Garantia", 
                        "No se puede modificar el tipo de préstamo de una garantía existente");
                }

                // No permitir cambiar el estado directamente
                if (garantia.getEstado() != null && !garantiaDb.getEstado().equals(garantia.getEstado())) {
                    throw new ActualizarEntidadExcepcion("Garantia", 
                        "No se puede modificar el estado directamente. Los cambios de estado se manejan a través de la lógica de negocio");
                }

                garantiaDb.setDescripcion(garantia.getDescripcion());
                garantiaDb.setTipoGarantia(garantia.getTipoGarantia());
                
                // Si el tipo es PERSONAL, establecer el valor a 0
                if ("PERSONAL".equals(garantia.getTipoGarantia())) {
                    garantiaDb.setValor(0);
                } else {
                    garantiaDb.setValor(garantia.getValor());
                }

                this.repositorio.save(garantiaDb);
            } else {
                throw new GarantiaNoEncontradoExcepcion("Garantia", 
                    "Error al actualizar la Garantia. No se encontró la Garantia con ID: " + garantia.getId());
            }
        } catch (Exception rte) {
            throw new ActualizarEntidadExcepcion("Garantia", "Error al actualizar la Garantia. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<Garantia> garantiaOpcional = this.repositorio.findById(id);
            if (garantiaOpcional.isPresent()) {
                this.repositorio.delete(garantiaOpcional.get());
            } else {
                throw new GarantiaNoEncontradoExcepcion("Garantia", 
                    "Error al eliminar la Garantia. No se encontró la Garantia con ID: " + id);
            }
        } catch (Exception rte) {
            throw new EliminarEntidadExcepcion("Garantia", "Error al eliminar la Garantia. Texto del error: "+rte.getMessage());
        }
    }

    private void validarGarantia(Garantia garantia) {
        // Validar tipo de préstamo
        if (garantia.getTipoPrestamo() == null || garantia.getTipoPrestamo().getIdTipoPrestamo() == null) {
            throw new CrearEntidadExcepcion("Garantia", "Debe especificar un tipo de préstamo válido");
        }

        // Verificar que el tipo de préstamo exista y esté activo
        Optional<TipoPrestamo> tipoPrestamoOpt = tipoPrestamoRepositorio.findById(garantia.getTipoPrestamo().getIdTipoPrestamo());
        if (!tipoPrestamoOpt.isPresent()) {
            throw new CrearEntidadExcepcion("Garantia", "El tipo de préstamo especificado no existe");
        }
        
        TipoPrestamo tipoPrestamo = tipoPrestamoOpt.get();
        if (!"ACTIVO".equals(tipoPrestamo.getEstado())) {
            throw new CrearEntidadExcepcion("Garantia", 
                "No se puede crear/actualizar una garantía para un tipo de préstamo inactivo");
        }

        // Validar descripción
        if (garantia.getDescripcion() == null || garantia.getDescripcion().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("Garantia", "La descripción es obligatoria");
        }
        if (garantia.getDescripcion().length() > 200) {
            throw new CrearEntidadExcepcion("Garantia", "La descripción no puede exceder los 200 caracteres");
        }

        // Validar tipo de garantía
        if (garantia.getTipoGarantia() == null || garantia.getTipoGarantia().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("Garantia", "El tipo de garantía es obligatorio");
        }
        if (!TIPOS_GARANTIA_PERMITIDOS.contains(garantia.getTipoGarantia())) {
            throw new CrearEntidadExcepcion("Garantia", 
                "El tipo de garantía debe ser uno de los valores permitidos: " + 
                String.join(", ", TIPOS_GARANTIA_PERMITIDOS));
        }

        // Validar valor según el tipo de garantía
        if ("PERSONAL".equals(garantia.getTipoGarantia())) {
            if (garantia.getValor() != null && garantia.getValor() != 0) {
                throw new CrearEntidadExcepcion("Garantia", 
                    "Para garantías de tipo PERSONAL no es necesario definir un valor, debe ser 0");
            }
            garantia.setValor(0);
        } else {
            if (garantia.getValor() == null || garantia.getValor() <= 0) {
                throw new CrearEntidadExcepcion("Garantia", "El valor debe ser mayor a cero para garantías no personales");
            }
        }
    }

    public List<Map<String, Object>> findAll() {
        try {
            List<Garantia> garantias = this.repositorio.findAll();
            return garantias.stream()
                .map(garantia -> {
                    Map<String, Object> resultado = new HashMap<>();
                    resultado.put("id", garantia.getId());
                    resultado.put("descripcion", garantia.getDescripcion());
                    resultado.put("tipoGarantia", garantia.getTipoGarantia());
                    resultado.put("valor", garantia.getValor());
                    resultado.put("estado", garantia.getEstado());
                    resultado.put("tipoPrestamo", garantia.getTipoPrestamo().getNombre());
                    return resultado;
                })
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusquedaExcepcion("Garantia", "Error al obtener todas las garantías: " + e.getMessage());
        }
    }
}
