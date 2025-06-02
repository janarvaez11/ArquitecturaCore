package com.banquito.core.aplicacion.prestamos.servicio;

import java.util.Optional;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

//import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
//import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.PrestamoComisionCargoExepcion;
import com.banquito.core.aplicacion.prestamos.modelo.Prestamo;
import com.banquito.core.aplicacion.prestamos.modelo.ComisionPrestamo;
import com.banquito.core.aplicacion.prestamos.modelo.PrestamoComisionCargo;
import com.banquito.core.aplicacion.prestamos.modelo.PrestamoComisionCargoId;
import com.banquito.core.aplicacion.prestamos.repositorio.PrestamoComisionCargoRepositorio;
import com.banquito.core.aplicacion.prestamos.repositorio.PrestamoRepositorio;
import com.banquito.core.aplicacion.prestamos.repositorio.ComisionPrestamoRepositorio;
import com.banquito.core.aplicacion.prestamos.excepcion.BusquedaExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.CondicionComision;
import com.banquito.core.aplicacion.prestamos.repositorio.CondicionComisionRepositorio;
import com.banquito.core.aplicacion.prestamos.servicio.CondicionComisionServicio;
import com.banquito.core.aplicacion.prestamos.modelo.PrestamosClientes;
import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.prestamos.repositorio.PrestamosClientesRepositorio;

import jakarta.transaction.Transactional;

@Service
public class PrestamoComisionCargoServicio {
    
    private final PrestamoComisionCargoRepositorio repositorio;
    private final PrestamoRepositorio prestamoRepositorio;
    private final ComisionPrestamoRepositorio comisionPrestamoRepositorio;
    private final CondicionComisionRepositorio condicionComisionRepositorio;
    private final CondicionComisionServicio condicionComisionServicio;
    private final PrestamosClientesRepositorio prestamosClientesRepositorio;

    public PrestamoComisionCargoServicio(
            PrestamoComisionCargoRepositorio repositorio,
            PrestamoRepositorio prestamoRepositorio,
            ComisionPrestamoRepositorio comisionPrestamoRepositorio,
            CondicionComisionRepositorio condicionComisionRepositorio,
            CondicionComisionServicio condicionComisionServicio,
            PrestamosClientesRepositorio prestamosClientesRepositorio) {
        this.repositorio = repositorio;
        this.prestamoRepositorio = prestamoRepositorio;
        this.comisionPrestamoRepositorio = comisionPrestamoRepositorio;
        this.condicionComisionRepositorio = condicionComisionRepositorio;
        this.condicionComisionServicio = condicionComisionServicio;
        this.prestamosClientesRepositorio = prestamosClientesRepositorio;
    }

    public PrestamoComisionCargo findById(PrestamoComisionCargoId id) {
        Optional<PrestamoComisionCargo> prestamoComisionCargoOpcional = this.repositorio.findById(id);
        if (prestamoComisionCargoOpcional.isPresent()) {
            return prestamoComisionCargoOpcional.get();
        } else {
            throw new PrestamoComisionCargoExepcion("PrestamoComisionCargo", 
                "Comisión de préstamo no encontrada con ID: " + id);
        }
    }

    @Transactional
    public void create(PrestamoComisionCargo prestamoComisionCargo) {
        try {
            // Validar que el préstamo existe
            Optional<Prestamo> prestamoOpcional = prestamoRepositorio.findById(prestamoComisionCargo.getId().getIdPrestamo());
            if (!prestamoOpcional.isPresent()) {
                throw new CrearEntidadExcepcion("PrestamoComisionCargo", 
                    "El préstamo con ID " + prestamoComisionCargo.getId().getIdPrestamo() + " no existe");
            }

            // Validar que la comisión existe
            Optional<ComisionPrestamo> comisionOpcional = comisionPrestamoRepositorio.findById(prestamoComisionCargo.getId().getIdComisionPrestamo());
            if (!comisionOpcional.isPresent()) {
                throw new CrearEntidadExcepcion("PrestamoComisionCargo", 
                    "La comisión con ID " + prestamoComisionCargo.getId().getIdComisionPrestamo() + " no existe");
            }

            // Validar que no existe ya esta combinación
            if (repositorio.existsById(prestamoComisionCargo.getId())) {
                throw new CrearEntidadExcepcion("PrestamoComisionCargo", 
                    "Ya existe una comisión asignada a este préstamo con estos IDs");
            }

            this.repositorio.save(prestamoComisionCargo);
        } catch (Exception rte) {
            throw new CrearEntidadExcepcion("PrestamoComisionCargo", 
                "Error al crear la comisión de préstamo. Texto del error: " + rte.getMessage());
        }
    }

    /*
    @Transactional
    public void update(PrestamoComisionCargo prestamoComisionCargo) {
        try {
            Optional<PrestamoComisionCargo> prestamoComisionCargoOpcional = 
                this.repositorio.findById(prestamoComisionCargo.getId());

            if (prestamoComisionCargoOpcional.isPresent()) {
                PrestamoComisionCargo prestamoComisionCargoDb = prestamoComisionCargoOpcional.get();
                prestamoComisionCargoDb.setFechaAsignacion(prestamoComisionCargo.getFechaAsignacion());
                this.repositorio.save(prestamoComisionCargoDb);
            } else {
                throw new PrestamoComisionCargoExepcion("PrestamoComisionCargo", 
                    "Error al actualizar la comisión de préstamo. No se encontró con ID: " + prestamoComisionCargo.getId());
            }
        } catch (Exception rte) {
            throw new ActualizarEntidadExcepcion("PrestamoComisionCargo", 
                "Error al actualizar la comisión de préstamo. Texto del error: " + rte.getMessage());
        }
    }
    */

    /*
    @Transactional
    public void delete(PrestamoComisionCargoId id) {
        try {
            Optional<PrestamoComisionCargo> prestamoComisionCargoOpcional = this.repositorio.findById(id);
            if (prestamoComisionCargoOpcional.isPresent()) {
                this.repositorio.delete(prestamoComisionCargoOpcional.get());
            } else {
                throw new PrestamoComisionCargoExepcion("PrestamoComisionCargo", 
                    "Error al eliminar la comisión de préstamo. No se encontró con ID: " + id);
            }
        } catch (Exception rte) {
            throw new EliminarEntidadExcepcion("PrestamoComisionCargo", 
                "Error al eliminar la comisión de préstamo. Texto del error: " + rte.getMessage());
        }
    }
    */

    public List<Map<String, Object>> findComisionesByPrestamo(Integer idPrestamo) {
        try {
            // Verificar que el préstamo existe
            Optional<Prestamo> prestamoOpt = prestamoRepositorio.findById(idPrestamo);
            if (!prestamoOpt.isPresent()) {
                throw new BusquedaExcepcion("PrestamoComisionCargo", 
                    "El préstamo con ID " + idPrestamo + " no existe");
            }

            Prestamo prestamo = prestamoOpt.get();
            List<PrestamoComisionCargo> comisiones = repositorio.findById_IdPrestamo(idPrestamo);

            // Obtener la relación prestamo-cliente
            List<PrestamosClientes> prestamosClientes = prestamosClientesRepositorio.findByIdPrestamo(prestamo);
            if (prestamosClientes.isEmpty()) {
                throw new BusquedaExcepcion("PrestamoComisionCargo", 
                    "No se encontró la relación prestamo-cliente para el préstamo con ID " + idPrestamo);
            }

            // Usar el primer cliente asociado al préstamo
            Cliente cliente = prestamosClientes.get(0).getIdCliente();

            return comisiones.stream()
                .map(comision -> {
                    Map<String, Object> resultado = new HashMap<>();
                    ComisionPrestamo comisionPrestamo = comision.getComisionPrestamo();
                    
                    // Información básica de la comisión
                    resultado.put("idComision", comisionPrestamo.getId());
                    resultado.put("nombre", comisionPrestamo.getNombre());
                    resultado.put("tipoComision", comisionPrestamo.getTipoComision());
                    resultado.put("tipoCalculo", comisionPrestamo.getTipoCalculo());
                    resultado.put("valor", comisionPrestamo.getValor());
                    resultado.put("fechaAsignacion", comision.getFechaAsignacion());

                    // Obtener y validar condiciones
                    List<CondicionComision> condiciones = condicionComisionRepositorio
                        .findByComisionPrestamo(comisionPrestamo);
                    
                    List<Map<String, Object>> condicionesValidadas = condiciones.stream()
                        .map(condicion -> {
                            Map<String, Object> condicionMap = new HashMap<>();
                            condicionMap.put("tipoCondicion", condicion.getTipoCondicion());
                            condicionMap.put("valor", condicion.getValor());
                            condicionMap.put("valorTexto", condicion.getValorTexto());
                            
                            // Validar la condición
                            boolean cumpleCondicion = condicionComisionServicio
                                .validarCondicion(condicion, prestamo, cliente);
                            condicionMap.put("cumpleCondicion", cumpleCondicion);
                            
                            return condicionMap;
                        })
                        .collect(Collectors.toList());
                    
                    resultado.put("condiciones", condicionesValidadas);
                    
                    // Verificar si cumple todas las condiciones
                    boolean aplicaComision = condicionesValidadas.stream()
                        .allMatch(condicion -> (boolean)condicion.get("cumpleCondicion"));
                    resultado.put("aplicaComision", aplicaComision);

                    return resultado;
                })
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusquedaExcepcion("PrestamoComisionCargo", 
                "Error al buscar comisiones del préstamo: " + e.getMessage());
        }
    }
} 