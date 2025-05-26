package com.banquito.core.aplicacion.cuentas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private TipoCuentaRepositorio tipoCuentaRepositorio;

    public List<TipoCuenta> findAll() {
        return tipoCuentaRepositorio.findAll();
    }

    public Page<TipoCuenta> listarTodos(Pageable pageable) {
        return tipoCuentaRepositorio.findAll(pageable);
    }

    public TipoCuenta findById(Integer id) {
        Optional<TipoCuenta> tipoCuentaOpt = tipoCuentaRepositorio.findById(id);
        if (!tipoCuentaOpt.isPresent()) {
            throw new TipoCuentaNoEncontradaExcepcion("Tipo Cuenta", "No se encontró el tipo de cuenta con ID: " + id);
        }
        return tipoCuentaOpt.get();
    }

    @Transactional
    public void create(TipoCuenta tipoCuenta) {
        try {
            this.tipoCuentaRepositorio.save(tipoCuenta);
        } catch (Exception e) {
            throw new CrearEntidadExcepcion("Tipo Cuentas", "Error al crear el Tipo de Cuenta. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void update(TipoCuenta tipoCuenta) {
        try {
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
                throw new TipoCuentaNoEncontradaExcepcion("Tipo Cuenta", 
                    "No se encontró el tipo de cuenta con ID: " + tipoCuenta.getIdTipoCuenta());
            }
        } catch (Exception e) {
            throw new ActualizarEntidadExcepcion("Tipo Cuentas", 
                "Error al actualizar el Tipo de Cuenta. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<TipoCuenta> tipoOptional = this.tipoCuentaRepositorio.findById(id);
            if (tipoOptional.isPresent()) {
                this.tipoCuentaRepositorio.delete(tipoOptional.get());
            } else {
                throw new TipoCuentaNoEncontradaExcepcion("Tipo Cuenta", 
                    "No se encontró el tipo de cuenta con ID: " + id);
            }
        } catch (Exception e) {
            throw new EliminarEntidadExcepcion("Tipo Cuentas", 
                "Error al eliminar el Tipo de Cuenta. Error: " + e.getMessage());
        }
    }

    public List<TipoCuenta> buscarPorNombre(String nombre) {
        return tipoCuentaRepositorio.findByNombreContainingIgnoreCase(nombre);
    }

    public List<TipoCuenta> buscarPorMoneda(String idMoneda) {
        return tipoCuentaRepositorio.findByIdMoneda(idMoneda);
    }

    public List<TipoCuenta> buscarPorTipoCliente(String tipoCliente) {
        return tipoCuentaRepositorio.findByTipoCliente(tipoCliente);
    }

    public TipoCuenta findDefaultTipoCuenta() {
        List<TipoCuenta> list = this.tipoCuentaRepositorio.findAll();
        if (!list.isEmpty()) {
            return list.getFirst();
        } else {
            throw new TipoCuentaNoEncontradaExcepcion("No existen registros de tipo de cuenta");
        }
    }
}
