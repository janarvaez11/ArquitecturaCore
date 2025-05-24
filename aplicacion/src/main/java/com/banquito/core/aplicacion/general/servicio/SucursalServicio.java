package com.banquito.core.aplicacion.general.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.general.modelo.Sucursal;
import com.banquito.core.aplicacion.general.repositorio.SucursalRepositorio;
import com.banquito.core.aplicacion.general.excepcion.CrearSucursalExcepcion;
import com.banquito.core.aplicacion.general.excepcion.ActualizarSucursalExcepcion;
import com.banquito.core.aplicacion.general.excepcion.SucursalNoEncontradaExcepcion;

@Service
public class SucursalServicio {

    private final SucursalRepositorio sucursalRepositorio;

    public SucursalServicio(SucursalRepositorio sucursalRepositorio) {
        this.sucursalRepositorio = sucursalRepositorio;
    }

    /**
     * Crea una nueva sucursal.
     */
    @Transactional
    public void crearSucursal(Sucursal sucursal) {
        try {
            if (this.sucursalRepositorio.existsById(sucursal.getCodigo())) {
                throw new CrearSucursalExcepcion("Sucursal", "La sucursal con código " + sucursal.getCodigo() + " ya existe.");
            }
            this.sucursalRepositorio.save(sucursal);
        } catch (RuntimeException e) {
            throw new CrearSucursalExcepcion("Sucursal", "Error al crear la sucursal: " + e.getMessage());
        }
    }

    /**
     * Actualiza los datos de una sucursal existente.
     */
    @Transactional
    public void actualizarSucursal(Sucursal sucursal) {
        try {
            Optional<Sucursal> optional = this.sucursalRepositorio.findById(sucursal.getCodigo());
            if (optional.isPresent()) {
                Sucursal sucursalDB = optional.get();
                sucursalDB.setNombre(sucursal.getNombre());
                sucursalDB.setDireccion(sucursal.getDireccion());
                sucursalDB.setTelefono(sucursal.getTelefono());
                sucursalDB.setEstado(sucursal.getEstado());
                sucursalDB.setLocacion(sucursal.getLocacion());
                this.sucursalRepositorio.save(sucursalDB);
            } else {
                throw new SucursalNoEncontradaExcepcion("No se encontró la sucursal con código: " + sucursal.getCodigo());
            }
        } catch (RuntimeException e) {
            throw new ActualizarSucursalExcepcion("Sucursal", "Error al actualizar la sucursal: " + e.getMessage());
        }
    }

    /**
     * Obtiene una sucursal por su código.
     */
    public Sucursal obtenerPorCodigo(String codigo) {
        return this.sucursalRepositorio.findById(codigo)
                .orElseThrow(() -> new SucursalNoEncontradaExcepcion("Sucursal no encontrada con código: " + codigo));
    }

    /**
     * Lista todas las sucursales.
     */
    public List<Sucursal> listarTodas() {
        List<Sucursal> sucursales = this.sucursalRepositorio.findAll();
        if (sucursales.isEmpty()) {
            throw new SucursalNoEncontradaExcepcion("No hay sucursales registradas en el sistema.");
        }
        return sucursales;
    }

    /**
     * Lista solo las sucursales activas (estado = 'ACT').
     */
    public List<Sucursal> listarActivas() {
        List<Sucursal> sucursales = this.sucursalRepositorio.findByEstado("ACT");
        if (sucursales.isEmpty()) {
            throw new SucursalNoEncontradaExcepcion("No hay sucursales activas registradas en el sistema.");
        }
        return sucursales;
    }

    /**
     * Verifica si una sucursal existe por su código.
     */
    public boolean existeSucursal(String codigo) {
        return this.sucursalRepositorio.existsById(codigo);
    }

    /**
     * Busca sucursales por locación geográfica.
     */
    public List<Sucursal> buscarPorLocacion(Integer idLocacion) {
        List<Sucursal> sucursales = this.sucursalRepositorio.findByLocacionId(idLocacion);
        if (sucursales.isEmpty()) {
            throw new SucursalNoEncontradaExcepcion("No hay sucursales asociadas a la locación geográfica con ID: " + idLocacion);
        }
        return sucursales;
    }
}