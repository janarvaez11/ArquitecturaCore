package com.banquito.core.aplicacion.general.servicio;

import com.banquito.core.aplicacion.general.excepcion.ActualizarEntidadException;
import com.banquito.core.aplicacion.general.excepcion.CrearEntidadException;
import com.banquito.core.aplicacion.general.excepcion.EliminarEntidadException;
import com.banquito.core.aplicacion.general.excepcion.EntidadBancariaNoEncontradaException;
import com.banquito.core.aplicacion.general.modelo.EntidadBancaria;
import com.banquito.core.aplicacion.general.repositorio.EntidadBancariaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EntidadBancariaServicio {
    private final EntidadBancariaRepositorio repositorio;

    public EntidadBancariaServicio(EntidadBancariaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public EntidadBancaria EncotrarporId(Integer id){
        Optional<EntidadBancaria> entidadBancariaOptional = this.repositorio.findById(id);
        if (entidadBancariaOptional.isPresent()) {
            return entidadBancariaOptional.get();
        } else {
            throw new EntidadBancariaNoEncontradaException("EntidadBancaria", "El ID: " + id + " no corresponde a ninguna registro");
        }
    }

    public List<EntidadBancaria> ListarEntidadesBancarias(){
        List<EntidadBancaria> entidadBancarias = this.repositorio.findAll();
        if (!entidadBancarias.isEmpty()) {
            return entidadBancarias;
        }else {
            throw new EntidadBancariaNoEncontradaException("EntidadBancaria", "No existen entidades bancarias registradas");
        }

    }

    @Transactional
    public void crearEntidadBancaria(EntidadBancaria entidadBancaria) {
        try {
            this.repositorio.save(entidadBancaria);
        } catch (RuntimeException rte) {
            throw new CrearEntidadException("EntidadBancaria", "Error al crear la entidad bancaria. Texto del error: " + rte.getMessage());
        }
    }

    @Transactional
    public void actualizarEntidadBancaria(EntidadBancaria entidadBancaria) {
        try {
            Optional<EntidadBancaria> entidadBancariaOptional = this.repositorio.findById(entidadBancaria.getId());
            if (entidadBancariaOptional.isPresent()) {
                EntidadBancaria entidadBancariaDb = entidadBancariaOptional.get();
                entidadBancariaDb.setCodigoLocal(entidadBancaria.getCodigoLocal());
                entidadBancariaDb.setNombre(entidadBancaria.getNombre());
                entidadBancariaDb.setCodigoInternacional(entidadBancaria.getCodigoInternacional());

                this.repositorio.save(entidadBancariaDb);
            } else {
                throw new EntidadBancariaNoEncontradaException("EntidadBancaria", "Error al actualizar la entidad bancaria. No se encontró la entidad bancaria con ID: " + entidadBancaria.getId());
            }
        } catch (RuntimeException rte) {
            throw new ActualizarEntidadException("EntidadBancaria", "Error al actualizar la entidad bancaria. Texto del error: " + rte.getMessage());
        }
    }

    @Transactional
    public void eliminarEntidadBancaria(Integer id) {
        try {
            Optional<EntidadBancaria> entidadBancariaOptional = this.repositorio.findById(id);
            if (entidadBancariaOptional.isPresent()) {
                this.repositorio.delete(entidadBancariaOptional.get());
            } else {
                throw new EntidadBancariaNoEncontradaException("EntidadBancaria", "Error al eliminar la entidad bancaria. No se encontró la entidad bancaria con ID: " + id);
            }
        } catch (RuntimeException rte) {
            throw new EliminarEntidadException("EntidadBancaria", "Error al eliminar la entidad bancaria. Texto del error: " + rte.getMessage());
        }
    }

}
