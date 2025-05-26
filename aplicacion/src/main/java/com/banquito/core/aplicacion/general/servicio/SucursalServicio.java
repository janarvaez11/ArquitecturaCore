package com.banquito.core.aplicacion.general.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.general.modelo.Sucursal;
import com.banquito.core.aplicacion.general.repositorio.SucursalRepositorio;
import com.banquito.core.aplicacion.general.excepcion.*;

@Service
public class SucursalServicio {

    private final SucursalRepositorio sucursalRepositorio;

    public SucursalServicio(SucursalRepositorio sucursalRepositorio) {
        this.sucursalRepositorio = sucursalRepositorio;
    }

    @Transactional
    public void crearSucursal(Sucursal sucursal) {
        if (sucursalRepositorio.existsByCodigo(sucursal.getCodigo())) {
            throw new CrearSucursalExcepcion("Ya existe una sucursal con el código: " + sucursal.getCodigo(), null);
        }
        sucursal.setEstado("ACT");
        this.sucursalRepositorio.save(sucursal);
    }

    @Transactional
    public void actualizarSucursal(String codigo, Sucursal sucursal) {
        Sucursal sucursalDB = obtenerPorCodigo(codigo);
        
        sucursalDB.setNombre(sucursal.getNombre());
        sucursalDB.setTelefono(sucursal.getTelefono());
        sucursalDB.setCorreoElectronico(sucursal.getCorreoElectronico());
        sucursalDB.setLatitud(sucursal.getLatitud());
        sucursalDB.setLongitud(sucursal.getLongitud());
        sucursalDB.setLinea1(sucursal.getLinea1());
        sucursalDB.setLinea2(sucursal.getLinea2());
        sucursalDB.setEstado(sucursal.getEstado());
        
        this.sucursalRepositorio.save(sucursalDB);
    }

    @Transactional
    public void eliminarLogico(String codigo) {
        Sucursal sucursal = obtenerPorCodigo(codigo);
        sucursal.setEstado("INA");
        this.sucursalRepositorio.save(sucursal);
    }

    public Sucursal obtenerPorCodigo(String codigo) {
        return this.sucursalRepositorio.findByCodigo(codigo)
                .orElseThrow(() -> new SucursalNoEncontradaExcepcion("Sucursal no encontrada con código: " + codigo));
    }

    public List<Sucursal> listarTodas() {
        return this.sucursalRepositorio.findAll();
    }

    public List<Sucursal> listarActivas() {
        return this.sucursalRepositorio.findByEstado("ACT");
    }

    public List<Sucursal> buscarPorLocacion(Integer idLocacion) {
        return this.sucursalRepositorio.findByLocacionId(idLocacion);
    }

    public List<Sucursal> buscarPorEntidadBancariaYEstado(Integer idEntidadBancaria, String estado) {
        return this.sucursalRepositorio.findByEntidadBancariaIdAndEstado(idEntidadBancaria, estado);
    }
}