package com.banquito.core.aplicacion.cuentas.servicio;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.banquito.core.aplicacion.general.modelo.Moneda;
import com.banquito.core.aplicacion.general.repositorio.MonedaRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.TipoCuenta;
import com.banquito.core.aplicacion.cuentas.repositorio.TipoCuentaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class TipoCuentaServicio {
    
    private final TipoCuentaRepositorio tipoCuentaRepositorio;
    private final MonedaRepositorio monedaRepositorio;

    public TipoCuentaServicio(TipoCuentaRepositorio tipoCuentaRepositorio, MonedaRepositorio monedaRepositorio) {
        this.tipoCuentaRepositorio = tipoCuentaRepositorio;
        this.monedaRepositorio = monedaRepositorio;
    }

    public List<Map<String, Object>> listarTodos() {
        List<TipoCuenta> tipos = tipoCuentaRepositorio.findAll();
        if (tipos.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("TipoCuenta", "No existen tipos de cuenta registrados");
        }
        return tipos.stream().map(this::mapTipoCuenta).toList();
    }

    public Page<TipoCuenta> listarTodosPaginado(Pageable pageable) {
        Page<TipoCuenta> page = tipoCuentaRepositorio.findAll(pageable);
        if (page.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("TipoCuenta", "No existen tipos de cuenta registrados");
        }
        return page;
    }

    public TipoCuenta buscarPorId(Integer id) {
        return tipoCuentaRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("TipoCuenta",
                    "No se encontró el tipo de cuenta con ID: " + id));
    }

    public Map<String, Object> buscarPorIdMapeado(Integer id) {
        TipoCuenta tipoCuenta = tipoCuentaRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("TipoCuenta",
                        "No se encontró el tipo de cuenta con ID: " + id));
        return mapTipoCuenta(tipoCuenta);
    }



    @Transactional
    public TipoCuenta crear(TipoCuenta tipoCuenta) {
        try {
            validarTipoCuenta(tipoCuenta);
            if (tipoCuenta.getMoneda() != null && tipoCuenta.getMoneda().getId() != null) {
                Moneda moneda = monedaRepositorio.findById(tipoCuenta.getMoneda().getId())
                        .orElseThrow(() -> new CrearEntidadExcepcion("TipoCuenta", "La moneda no existe"));
                tipoCuenta.setMoneda(moneda);
            } else {
                throw new CrearEntidadExcepcion("TipoCuenta", "La moneda es obligatoria");
            }
            return this.tipoCuentaRepositorio.save(tipoCuenta);

        } catch (RuntimeException e) {
            throw new CrearEntidadExcepcion("TipoCuenta", 
                "Error al crear el tipo de cuenta: " + e.getMessage());
        }
    }

    @Transactional
    public TipoCuenta actualizar(Integer id, TipoCuenta tipoCuenta) {
        try {
            TipoCuenta tipoCuentaDb = buscarPorId(id);
            validarTipoCuenta(tipoCuenta);
            
            tipoCuentaDb.setNombre(tipoCuenta.getNombre());
            tipoCuentaDb.setDescripcion(tipoCuenta.getDescripcion());
            tipoCuentaDb.setTipoCliente(tipoCuenta.getTipoCliente());
            tipoCuentaDb.setEstado(tipoCuenta.getEstado());
            
            return this.tipoCuentaRepositorio.save(tipoCuentaDb);
        } catch (RuntimeException e) {
            throw new ActualizarEntidadExcepcion("TipoCuenta", 
                "Error al actualizar el tipo de cuenta: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        try {
            TipoCuenta tipoCuenta = buscarPorId(id);
            if ("ACTIVO".equals(tipoCuenta.getEstado())) {
                throw new EliminarEntidadExcepcion("TipoCuenta", 
                    "No se puede eliminar un tipo de cuenta en estado ACTIVO");
            }
            this.tipoCuentaRepositorio.delete(tipoCuenta);
        } catch (RuntimeException e) {
            throw new EliminarEntidadExcepcion("TipoCuenta", 
                "Error al eliminar el tipo de cuenta: " + e.getMessage());
        }
    }

    public List<Map<String, Object>> buscarPorNombre(String nombre) {
        List<TipoCuenta> tipos = tipoCuentaRepositorio.findByNombreContainingIgnoreCase(nombre);
        if (tipos.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("TipoCuenta",
                    "No se encontraron tipos de cuenta con el nombre: " + nombre);
        }
        return tipos.stream().map(this::mapTipoCuenta).toList();
    }


    public List<Map<String, Object>> buscarPorMoneda(String idMoneda) {
        List<TipoCuenta> tipos = tipoCuentaRepositorio.findByMonedaId(idMoneda);
        if (tipos.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("TipoCuenta",
                    "No se encontraron tipos de cuenta para la moneda: " + idMoneda);
        }
        return tipos.stream().map(this::mapTipoCuenta).toList();
    }


    public List<Map<String, Object>> buscarPorTipoCliente(String tipoCliente) {
        List<TipoCuenta> tipos = tipoCuentaRepositorio.findByTipoCliente(tipoCliente);
        if (tipos.isEmpty()) {
            throw new EntidadNoEncontradaExcepcion("TipoCuenta",
                    "No se encontraron tipos de cuenta para el tipo de cliente: " + tipoCliente);
        }
        return tipos.stream().map(this::mapTipoCuenta).toList();
    }

    private void validarTipoCuenta(TipoCuenta tipoCuenta) {
        if (tipoCuenta.getNombre() == null || tipoCuenta.getNombre().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("TipoCuenta", "El nombre es obligatorio");
        }
        if (tipoCuenta.getDescripcion() == null || tipoCuenta.getDescripcion().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("TipoCuenta", "La descripción es obligatoria");
        }
        if (tipoCuenta.getEstado() == null || tipoCuenta.getEstado().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("TipoCuenta", "El estado es obligatorio");
        }
        if (!tipoCuenta.getEstado().equals("ACTIVO") && !tipoCuenta.getEstado().equals("INACTIVO")) {
            throw new CrearEntidadExcepcion("TipoCuenta", "El estado debe ser ACTIVO o INACTIVO");
        }
        if (tipoCuenta.getTipoCliente() == null || tipoCuenta.getTipoCliente().trim().isEmpty()) {
            throw new CrearEntidadExcepcion("TipoCuenta", "El tipo de cliente es obligatorio");
        }
    }

    private Map<String, Object> mapTipoCuenta(TipoCuenta tipoCuenta) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("idTipoCuenta", tipoCuenta.getIdTipoCuenta());
        map.put("nombre", tipoCuenta.getNombre());
        map.put("descripcion", tipoCuenta.getDescripcion());
        map.put("requisitosApertura", tipoCuenta.getRequisitosApertura());
        map.put("tipoCliente", tipoCuenta.getTipoCliente());
        map.put("cuentasContablesAsociadas", tipoCuenta.getCuentasContablesAsociadas());
        map.put("estado", tipoCuenta.getEstado());
        map.put("fechaCreacion", tipoCuenta.getFechaCreacion());
        map.put("fechaModificacion", tipoCuenta.getFechaModificacion());
        map.put("tasaInteresId", tipoCuenta.getTasaInteres() != null ? tipoCuenta.getTasaInteres().getIdTasaInteres() : null);
        map.put("moneda", tipoCuenta.getMoneda() != null ? tipoCuenta.getMoneda().getNombre() : null);
        return map;
    }
}
