package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.excepcion.ActualizarExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.CrearExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.EliminarExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.NoEncontradoExcepcion;
import com.banquito.core.aplicacion.clientes.modelo.Empresa;
import com.banquito.core.aplicacion.clientes.repositorio.EmpresaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaServicio {
    private final EmpresaRepositorio empresaRepositorio;

    public EmpresaServicio(EmpresaRepositorio empresaRepositorio) {
        this.empresaRepositorio = empresaRepositorio;
    }

    public List<Empresa> buscarTodas() {
        return empresaRepositorio.findAll();
    }

    public Empresa buscarPorId(Integer id) {
        Optional<Empresa> empresa = empresaRepositorio.findById(id);
        if (empresa.isPresent()) {
            return empresa.get();
        }else {
            throw new NoEncontradoExcepcion("Empresa","Error al buscar la empresa:" + id);
        }
    }

    public Empresa buscarPorNumeroIdentificacion(String numeroIdentificacion) {
        Optional<Empresa> empresa = empresaRepositorio.findByNumeroIdentificacion(numeroIdentificacion);
        if (empresa.isPresent()) {
            return empresa.get();
        } else {
            throw new NoEncontradoExcepcion("Empresa","Empresa no encontrada con número de identificación: " + numeroIdentificacion);
        }
    }


    @Transactional
    public void crear(Empresa empresa) {
        try {
            empresaRepositorio.save(empresa);
        }catch (RuntimeException rte){
            throw new CrearExcepcion("Error al crear la empresa:" + rte.getMessage(), "Empresa");
        }
    }

    @Transactional
    public void actualizar(Empresa empresa) {
        try{
            Optional<Empresa> empresaOptional = this.empresaRepositorio.findById(empresa.getIdEmpresa());
            if (empresaOptional.isPresent()) {
                Empresa empresaDB = empresaOptional.get();

                if (empresa.getNombreEmpresa() != null) {
                    empresaDB.setNombreEmpresa(empresa.getNombreEmpresa());
                }

                if (empresa.getCorreoElectronico() != null) {
                    empresaDB.setCorreoElectronico(empresa.getCorreoElectronico());
                }

                if (empresa.getTipoCompania() != null) {
                    empresaDB.setTipoCompania(empresa.getTipoCompania());
                }

                if (empresa.getEstado() != null) {
                    empresaDB.setEstado(empresa.getEstado());
                }

                if (empresa.getSectorEconomico() != null) {
                    empresaDB.setSectorEconomico(empresa.getSectorEconomico());
                }

                empresaRepositorio.save(empresaDB);
            }else {
                throw new ActualizarExcepcion(String.valueOf(empresa.getIdEmpresa()), "Empresa");
            }
        }catch (RuntimeException rte){
            throw new ActualizarExcepcion("Error al actualizar la empresa:" + rte.getMessage(), "Empresa");
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        try {
            Optional<Empresa> empresaOptional = empresaRepositorio.findById(id);

            if (empresaOptional.isPresent()) {
                empresaRepositorio.deleteById(id);
            }else {
                throw new EliminarExcepcion(String.valueOf(id), "Empresa");
            }
        }catch (RuntimeException rte){
            throw new EliminarExcepcion("Error al eliminar la empresa:" +rte.getMessage(), "Empresa");
        }
    }
}
