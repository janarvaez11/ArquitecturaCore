package com.banquito.core.aplicacion.prestamos.servicio;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.BusquedaExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.PrestamoNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.Prestamo;
import com.banquito.core.aplicacion.prestamos.repositorio.PrestamoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class PrestamoServicio {
    
    private final PrestamoRepositorio repositorio;
    
    // Lista de bases de cálculo permitidas
    private static final List<String> BASES_CALCULO_PERMITIDAS = Arrays.asList(
        "30/360",
        "31/365",
        "Actual/360",
        "Actual/365"
    );

    // Lista de estados permitidos
    private static final List<String> ESTADOS_PERMITIDOS = Arrays.asList(
        "SOLICITADO",
        "APROBADO",
        "RECHAZADO",
        "DESEMBOLSADO",
        "CANCELADO"
    );

    public PrestamoServicio(PrestamoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Prestamo findById(Integer id) {
        Optional<Prestamo> prestamoOpcional = this.repositorio.findById(id);
        if (prestamoOpcional.isPresent()) {
            return prestamoOpcional.get();
        } else {
            throw new PrestamoNoEncontradoExcepcion("Prestamos","Tipo de préstamo no encontrado con ID: " + id);
        }
    }

    @Transactional
    public void create(Prestamo prestamo) {
        try {
            validarPrestamo(prestamo);
            
            // Establecer valores por defecto
            prestamo.setEstado("SOLICITADO");
            prestamo.setFechaModificacion(new Date());
            
            this.repositorio.save(prestamo);
        } catch (Exception rte) {
            throw new CrearEntidadExcepcion("Prestamos", "Error al crear el prestamo. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void update(Prestamo prestamo) {
        try {
            validarPrestamo(prestamo);
            Optional<Prestamo> prestamoOpcional = this.repositorio.findById(prestamo.getId());

            if (prestamoOpcional.isPresent()) {
                Prestamo prestamoDb = prestamoOpcional.get();
                
                // Actualizar campos permitidos
                prestamoDb.setTipoPrestamo(prestamo.getTipoPrestamo());
                prestamoDb.setNombre(prestamo.getNombre());
                prestamoDb.setDescripcion(prestamo.getDescripcion());
                prestamoDb.setEstado(prestamo.getEstado());
                prestamoDb.setBaseCalculo(prestamo.getBaseCalculo());
                prestamoDb.setTasaMonetaria(prestamo.getTasaMonetaria());
                
                // Actualizar fecha de modificación
                prestamoDb.setFechaModificacion(new Date());

                this.repositorio.save(prestamoDb);
            } else {
                throw new PrestamoNoEncontradoExcepcion("Prestamo", 
                    "Error al actualizar el prestamo. No se encontró el tipo de prestamo con ID: " + prestamo.getId());
            }
        } catch (Exception rte) {
            throw new ActualizarEntidadExcepcion("Prestamos", "Error al actualizar el prestamo. Texto del error: "+rte.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<Prestamo> prestamoOpcional = this.repositorio.findById(id);
            if (prestamoOpcional.isPresent()) {
                this.repositorio.delete(prestamoOpcional.get());
            } else {
                throw new PrestamoNoEncontradoExcepcion("Prestamo", 
                    "Error al eliminar el prestamo. No se encontró el tipo de prestamo con ID: " + id);
            }
        } catch (Exception rte) {
            throw new EliminarEntidadExcepcion("Prestamos", "Error al eliminar el prestamo. Texto del error: "+rte.getMessage());
        }
    }

    private void validarPrestamo(Prestamo prestamo) {
        // Validar tipo de préstamo
        if (prestamo.getTipoPrestamo() == null || prestamo.getTipoPrestamo().getIdTipoPrestamo() == null) {
            throw new CrearEntidadExcepcion("Prestamo", "Debe especificar un tipo de préstamo válido");
        }

        // Validar nombre
        if (prestamo.getNombre() == null || prestamo.getNombre().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("Prestamo", "El nombre es obligatorio");
        }
        if (prestamo.getNombre().length() > 100) {
            throw new CrearEntidadExcepcion("Prestamo", "El nombre no puede exceder los 100 caracteres");
        }

        // Validar descripción
        if (prestamo.getDescripcion() == null || prestamo.getDescripcion().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("Prestamo", "La descripción es obligatoria");
        }
        if (prestamo.getDescripcion().length() > 200) {
            throw new CrearEntidadExcepcion("Prestamo", "La descripción no puede exceder los 200 caracteres");
        }

        // Validar base de cálculo
        if (prestamo.getBaseCalculo() == null || prestamo.getBaseCalculo().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("Prestamo", "La base de cálculo es obligatoria");
        }
        if (!BASES_CALCULO_PERMITIDAS.contains(prestamo.getBaseCalculo())) {
            throw new CrearEntidadExcepcion("Prestamo", 
                "La base de cálculo debe ser uno de los valores permitidos: " + 
                String.join(", ", BASES_CALCULO_PERMITIDAS));
        }

        // Validar tasa moratoria
        if (prestamo.getTasaMonetaria() == null || prestamo.getTasaMonetaria().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new CrearEntidadExcepcion("Prestamo", "La tasa moratoria debe ser mayor a cero");
        }
    }

    public List<Prestamo> findByEstado(String estado) {
        try {
            if (estado == null || estado.trim().isEmpty()) {
                throw new BusquedaExcepcion("Prestamo", "El estado no puede estar vacío");
            }
            if (!ESTADOS_PERMITIDOS.contains(estado)) {
                throw new BusquedaExcepcion("Prestamo",
                    "El estado debe ser uno de los valores permitidos: " +
                    String.join(", ", ESTADOS_PERMITIDOS));
            }
            return this.repositorio.findByEstado(estado);
        } catch (BusquedaExcepcion e) {
            throw e;
        } catch (Exception e) {
            throw new BusquedaExcepcion("Prestamo",
                "Error al buscar préstamos por estado: " + estado, e);
        }
    }
}
