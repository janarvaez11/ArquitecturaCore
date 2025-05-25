package com.banquito.core.aplicacion.cuentas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.TipoCuentaNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.TipoCuenta;
import com.banquito.core.aplicacion.cuentas.repositorio.TipoCuentaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class TipoCuentaServicio {

    private final TipoCuentaRepositorio tipoCuentaRepositorio;

    public TipoCuentaServicio(TipoCuentaRepositorio tipoCuentaRepositorio) {
        this.tipoCuentaRepositorio = tipoCuentaRepositorio;
    }

    public TipoCuenta findById(Integer id) {
        Optional<TipoCuenta> tipoCuentaOptional = this.tipoCuentaRepositorio.findById(id);
        if (tipoCuentaOptional.isPresent()) {
            return tipoCuentaOptional.get();
        } else {
            throw new TipoCuentaNoEncontradaExcepcion("El id: " + id + " no corresponde a ningun registro");
        }
    }

    public TipoCuenta findDefaultTipoCuenta() {
        List<TipoCuenta> list = this.tipoCuentaRepositorio.findAll();
        if(list.isEmpty()){
            return list.getFirst();
        } else {
            throw new TipoCuentaNoEncontradaExcepcion("No existen registros de tipo de cuenta");
        }
    
    }

    @Transactional
    public void create (TipoCuenta tipoCuenta) {
        try {
            this.tipoCuentaRepositorio.save(tipoCuenta);
        } catch (Exception rte) {
            throw new CrearEntidadExcepcion("Tipo Cuentas", "Error al crear el Tipo de Cuenta. Texto del error: "+rte.getMessage());
        }
    }

    
    @Transactional
    public void update(TipoCuenta tipoCuenta) {
        try{
            Optional<TipoCuenta> tipoOptional = this.tipoCuentaRepositorio.findById(tipoCuenta.getIdTipoCuenta());

            if (tipoOptional.isPresent()) {
                TipoCuenta tipoCuentaDb = tipoOptional.get();
                tipoCuentaDb.setNombre(tipoCuenta.getNombre());
                tipoCuentaDb.setDescripcion(tipoCuenta.getDescripcion());
                tipoCuentaDb.setRequisitosApertura(tipoCuenta.getRequisitosApertura());
                tipoCuentaDb.setTipocliente(tipoCuenta.getTipocliente());
                tipoCuentaDb.setCuentasContablesAsociadas(tipoCuenta.getCuentasContablesAsociadas());
                tipoCuentaDb.setEstado(tipoCuenta.getEstado());
                tipoCuentaDb.setFechaModificacion(tipoCuenta.getFechaModificacion());
                this.tipoCuentaRepositorio.save(tipoCuentaDb);
            } else {
                throw new TipoCuentaNoEncontradaExcepcion("Tipo Cuenta", "Error al actualizar el Tipo de Cuenta. No se encontró el tipo de Cuenta con ID: " + tipoCuenta.getIdTipoCuenta());
            }
        } catch (Exception rte) {
            throw new ActualizarEntidadExcepcion("Tipo Cuentas", "Error al actualizar el Tipo de Cuenta. Texto del error: "+rte.getMessage());
        }
    }


    @Transactional
    public void delete(Integer id) {
        try {
            Optional<TipoCuenta> tipoOptional = this.tipoCuentaRepositorio.findById(id);
            if (tipoOptional.isPresent()) {
                this.tipoCuentaRepositorio.delete(tipoOptional.get());
            } else {
                throw new TipoCuentaNoEncontradaExcepcion("Tipo Cuenta", "Error al eliminar el Tipo de Cuenta. No se encontró el tipo de Cuenta con ID: " + id);
            }
        } catch (Exception rte) {
            throw new EliminarEntidadExcepcion("Tipo Cuentas", "Error al eliminar el Tipo de Cuenta. Texto del error: "+rte.getMessage());
        }
    }






}
