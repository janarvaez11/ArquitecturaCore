package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.excepcion.ActualizarExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.CrearExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.EliminarExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.NoEncontradoExcepcion;
import com.banquito.core.aplicacion.clientes.modelo.Empresa;
import com.banquito.core.aplicacion.clientes.repositorio.EmpresaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        validarCamposObligatorios(empresa);

        if (!"RUC".equalsIgnoreCase(empresa.getTipoIdentificacion())) {
            throw new CrearExcepcion("Solo se permite tipo de identificación: RUC", "Empresa");
        }

        boolean existe = empresaRepositorio.findByNumeroIdentificacion(empresa.getNumeroIdentificacion()).isPresent();
        if (existe) {
            throw new CrearExcepcion("Ya existe una empresa con el número de identificación: " + empresa.getNumeroIdentificacion(), "Empresa");
        }

        try {
            empresa.setFechaRegistro(new Date());
            empresa.setFechaActualizacion(new Date());
            empresaRepositorio.save(empresa);
        } catch (RuntimeException e) {
            throw new CrearExcepcion("Error al crear la empresa: " + e.getMessage(), "Empresa");
        }
    }

    @Transactional
    public void actualizar(Empresa empresa) {
        if (empresa.getIdEmpresa() == null) {
            throw new ActualizarExcepcion("ID de empresa no proporcionado", "Empresa");
        }

        try {
            Empresa empresaDB = empresaRepositorio.findById(empresa.getIdEmpresa())
                    .orElseThrow(() -> new ActualizarExcepcion(String.valueOf(empresa.getIdEmpresa()), "Empresa"));

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

            empresaDB.setFechaActualizacion(new Date());

            empresaRepositorio.save(empresaDB);
        } catch (RuntimeException e) {
            throw new ActualizarExcepcion("Error al actualizar la empresa: " + e.getMessage(), "Empresa");
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        if (id == null) {
            throw new EliminarExcepcion("ID de empresa no proporcionado", "Empresa");
        }
        try {
            Empresa empresa = empresaRepositorio.findById(id)
                    .orElseThrow(() -> new EliminarExcepcion(String.valueOf(id), "Empresa"));

            empresaRepositorio.delete(empresa);
        } catch (RuntimeException e) {
            throw new EliminarExcepcion("Error al eliminar la empresa: " + e.getMessage(), "Empresa");
        }
    }

    private void validarCamposObligatorios(Empresa empresa) {
        if (empresa.getTipoIdentificacion() == null || empresa.getNumeroIdentificacion() == null ||
                empresa.getNombreEmpresa() == null || empresa.getRazonSocial() == null ||
                empresa.getFechaConstitucion() == null || empresa.getCorreoElectronico() == null ||
                empresa.getTipoCompania() == null || empresa.getEstado() == null ||
                empresa.getSectorEconomico() == null) {

            throw new CrearExcepcion("Faltan campos obligatorios para la creación de la empresa", "Empresa");
        }

        if (!empresa.getTipoIdentificacion().equalsIgnoreCase("RUC")) {
            throw new CrearExcepcion("Tipo de identificación inválido. Solo se permite RUC", "Empresa");
        }
    }
}
