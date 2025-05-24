package com.banquito.core.aplicacion.cuentas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.cuentas.excepcion.TipoCuentaNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.TipoCuenta;
import com.banquito.core.aplicacion.cuentas.repositorio.TipoCuentaRepositorio;

@Service
public class TipoCuentaServicio {

    private final TipoCuentaRepositorio tipoCuentaRepositorio;

    public TipoCuentaServicio(TipoCuentaRepositorio tipoCuentaRepositorio) {
        this.tipoCuentaRepositorio = tipoCuentaRepositorio;
    }

    public TipoCuenta findById(Integer idTipoCuenta) {
        Optional<TipoCuenta> tipoCuentaOptional = this.tipoCuentaRepositorio.findById(idTipoCuenta);
        if (tipoCuentaOptional.isPresent()) {
            return tipoCuentaOptional.get();
        } else {
            throw new TipoCuentaNoEncontradaExcepcion("El id: " + idTipoCuenta + " no corresponde a ningun registro");
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


}
