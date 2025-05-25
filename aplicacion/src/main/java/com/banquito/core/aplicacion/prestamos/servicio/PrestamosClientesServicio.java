package com.banquito.core.aplicacion.prestamos.servicio;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.prestamos.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.prestamos.excepcion.PrestamoNoEncontradoExcepcion;
import com.banquito.core.aplicacion.prestamos.modelo.Prestamo;
import com.banquito.core.aplicacion.prestamos.modelo.PrestamosClientes;
import com.banquito.core.aplicacion.prestamos.repositorio.PrestamoRepositorio;
import com.banquito.core.aplicacion.prestamos.repositorio.PrestamosClientesRepositorio;

import jakarta.transaction.Transactional;

@Service
public class PrestamosClientesServicio {

    private final PrestamosClientesRepositorio repositorio;
    private final PrestamoRepositorio prestamoRepositorio;

    public PrestamosClientesServicio(PrestamosClientesRepositorio repositorio,
            PrestamoRepositorio prestamoRepositorio) {
        this.repositorio = repositorio;
        this.prestamoRepositorio = prestamoRepositorio;
    }

    public PrestamosClientes buscarPorId(Integer id) {
        Optional<PrestamosClientes> prestamoClienteOpcional = this.repositorio.findById(id);
        if (prestamoClienteOpcional.isPresent()) {
            return prestamoClienteOpcional.get();
        } else {
            throw new PrestamoNoEncontradoExcepcion("Prestamos Clientes",
                    "Préstamo del cliente no encontrado con ID: " + id);
        }
    }

    public List<PrestamosClientes> buscarPorPrestamo(Integer prestamoId) {
        Optional<Prestamo> prestamoOpt = this.prestamoRepositorio.findById(prestamoId);
        if (!prestamoOpt.isPresent()) {
            throw new PrestamoNoEncontradoExcepcion("Prestamos",
                    "Préstamo no encontrado con ID: " + prestamoId);
        }
        return this.repositorio.findByIdPrestamo(prestamoOpt.get());
    }

    public List<PrestamosClientes> buscarPorEstado(String estado) {
        return this.repositorio.findByEstado(estado);
    }

    public List<PrestamosClientes> buscarTodos() {
        return this.repositorio.findAll();
    }

    @Transactional
    public void crear(PrestamosClientes prestamoCliente) {
        try {
            validarPrestamoCliente(prestamoCliente);
            if (prestamoCliente.getIdPrestamo() != null && prestamoCliente.getIdPrestamo().getId() != null) {
                Optional<Prestamo> prestamoOpt = this.prestamoRepositorio.findById(
                        prestamoCliente.getIdPrestamo().getId());
                if (!prestamoOpt.isPresent()) {
                    throw new PrestamoNoEncontradoExcepcion("Prestamos",
                            "El préstamo especificado no existe");
                }
                prestamoCliente.setIdPrestamo(prestamoOpt.get());
            }

            prestamoCliente.setEstado("SOL"); // Estado inicial: SOLICITADO
            prestamoCliente.setFechaAprobacion(null);
            prestamoCliente.setFechaDesembolso(null);
            this.repositorio.save(prestamoCliente);
        } catch (Exception e) {
            throw new CrearEntidadExcepcion("Prestamos Clientes",
                    "Error al crear el préstamo del cliente: " + e.getMessage());
        }
    }

    @Transactional
    public void aprobar(Integer id) {
        try {
            Optional<PrestamosClientes> prestamoClienteOpt = this.repositorio.findById(id);
            if (prestamoClienteOpt.isPresent()) {
                PrestamosClientes prestamoCliente = prestamoClienteOpt.get();
                if (!"SOL".equals(prestamoCliente.getEstado())) {
                    throw new ActualizarEntidadExcepcion("Prestamos Clientes",
                            "Solo se pueden aprobar préstamos en estado SOLICITADO");
                }
                prestamoCliente.setEstado("APR");
                prestamoCliente.setFechaAprobacion(Instant.now());
                this.repositorio.save(prestamoCliente);
            } else {
                throw new PrestamoNoEncontradoExcepcion("Prestamos Clientes",
                        "Préstamo del cliente no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            throw new ActualizarEntidadExcepcion("Prestamos Clientes",
                    "Error al aprobar el préstamo del cliente: " + e.getMessage());
        }
    }

    @Transactional
    public void desembolsar(Integer id) {
        try {
            Optional<PrestamosClientes> prestamoClienteOpt = this.repositorio.findById(id);
            if (prestamoClienteOpt.isPresent()) {
                PrestamosClientes prestamoCliente = prestamoClienteOpt.get();
                if (!"APR".equals(prestamoCliente.getEstado())) {
                    throw new ActualizarEntidadExcepcion("Prestamos Clientes",
                            "Solo se pueden desembolsar préstamos en estado APROBADO");
                }
                prestamoCliente.setEstado("DES");
                prestamoCliente.setFechaDesembolso(Instant.now());
                this.repositorio.save(prestamoCliente);
            } else {
                throw new PrestamoNoEncontradoExcepcion("Prestamos Clientes",
                        "Préstamo del cliente no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            throw new ActualizarEntidadExcepcion("Prestamos Clientes",
                    "Error al desembolsar el préstamo del cliente: " + e.getMessage());
        }
    }

    @Transactional
    public void rechazar(Integer id) {
        try {
            Optional<PrestamosClientes> prestamoClienteOpt = this.repositorio.findById(id);
            if (prestamoClienteOpt.isPresent()) {
                PrestamosClientes prestamoCliente = prestamoClienteOpt.get();
                if (!"SOL".equals(prestamoCliente.getEstado())) {
                    throw new ActualizarEntidadExcepcion("Prestamos Clientes",
                            "Solo se pueden rechazar préstamos en estado SOLICITADO");
                }
                prestamoCliente.setEstado("REC");
                this.repositorio.save(prestamoCliente);
            } else {
                throw new PrestamoNoEncontradoExcepcion("Prestamos Clientes",
                        "Préstamo del cliente no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            throw new ActualizarEntidadExcepcion("Prestamos Clientes",
                    "Error al rechazar el préstamo del cliente: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        try {
            Optional<PrestamosClientes> prestamoClienteOpt = this.repositorio.findById(id);
            if (prestamoClienteOpt.isPresent()) {
                PrestamosClientes prestamoCliente = prestamoClienteOpt.get();
                // Solo se pueden eliminar préstamos en estado SOLICITADO o RECHAZADO
                if (!"SOL".equals(prestamoCliente.getEstado()) &&
                        !"REC".equals(prestamoCliente.getEstado())) {
                    throw new EliminarEntidadExcepcion("Prestamos Clientes",
                            "Solo se pueden eliminar préstamos en estado SOLICITADO o RECHAZADO");
                }
                this.repositorio.delete(prestamoCliente);
            } else {
                throw new PrestamoNoEncontradoExcepcion("Prestamos Clientes",
                        "Préstamo del cliente no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            throw new EliminarEntidadExcepcion("Prestamos Clientes",
                    "Error al eliminar el préstamo del cliente: " + e.getMessage());
        }
    }

    private void validarPrestamoCliente(PrestamosClientes prestamoCliente) {
        if (prestamoCliente == null) {
            throw new CrearEntidadExcepcion("Prestamos Clientes",
                    "El préstamo del cliente no puede ser nulo");
        }
        if (prestamoCliente.getIdPrestamo() == null) {
            throw new CrearEntidadExcepcion("Prestamos Clientes",
                    "El préstamo asociado es requerido");
        }
        if (prestamoCliente.getFechaVencimiento() == null) {
            throw new CrearEntidadExcepcion("Prestamos Clientes",
                    "La fecha de vencimiento es requerida");
        }
    }
}