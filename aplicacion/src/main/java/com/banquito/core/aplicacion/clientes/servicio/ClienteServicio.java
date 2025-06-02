package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.general.repositorio.SucursalRepositorio;
import com.banquito.core.aplicacion.clientes.repositorio.PersonaRepositorio;
import com.banquito.core.aplicacion.clientes.repositorio.ClienteRepositorio;
import com.banquito.core.aplicacion.clientes.repositorio.EmpresaRepositorio;
import com.banquito.core.aplicacion.general.repositorio.PaisRepositorio;
import com.banquito.core.aplicacion.clientes.excepcion.ActualizarClienteExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.ClienteNoEncontradoExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.CrearClienteExcepcion;
import com.banquito.core.aplicacion.clientes.modelo.Persona;
import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.clientes.modelo.Empresa;
import com.banquito.core.aplicacion.general.modelo.Sucursal;
import com.banquito.core.aplicacion.general.modelo.Pais;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class ClienteServicio {

    private final ClienteRepositorio clienteRepo;
    private final SucursalRepositorio sucursalRepo;
    private final PersonaRepositorio personaRepo;
    private final EmpresaRepositorio empresaRepo;
    private final PaisRepositorio paisRepo;


    public ClienteServicio(ClienteRepositorio clienteRepo,
            SucursalRepositorio sucursalRepo,
            PersonaRepositorio personaRepo,
            EmpresaRepositorio empresaRepo,
            PaisRepositorio paisRepo) {
        this.paisRepo = paisRepo;
        this.clienteRepo = clienteRepo;
        this.sucursalRepo = sucursalRepo;
        this.personaRepo = personaRepo;
        this.empresaRepo = empresaRepo;
    }

    public Cliente crearCliente(Cliente cliente) {
        try {
            Sucursal sucursal = sucursalRepo.findById(cliente.getSucursal().getIdSucursal())
                    .orElseThrow(() -> new CrearClienteExcepcion(
                            "Sucursal no encontrada con ID: " + cliente.getSucursal().getIdSucursal()));
            cliente.setSucursal(sucursal);

            if ("PERSONA".equalsIgnoreCase(cliente.getTipoEntidad())) {
                Persona persona = personaRepo.findById(cliente.getIdEntidad())
                        .orElseThrow(() -> new CrearClienteExcepcion(
                                "Persona no encontrada con ID: " + cliente.getIdEntidad()));

                cliente.setNombre(persona.getNombres());
                cliente.setTipoIdentificacion(persona.getTipoIdentificacion());
                cliente.setNumeroIdentificacion(persona.getNumeroIdentificacion());

                if (persona.getNacionalidad() != null) {
                    Pais pais = paisRepo.findById(persona.getNacionalidad())
                            .orElseThrow(() -> new CrearClienteExcepcion(
                                    "País no encontrado con código: " + persona.getNacionalidad()));
                    cliente.setPais(pais);
                }

            } else if ("EMPRESA".equalsIgnoreCase(cliente.getTipoEntidad())) {
                Empresa empresa = empresaRepo.findById(cliente.getIdEntidad())
                        .orElseThrow(() -> new CrearClienteExcepcion(
                                "Empresa no encontrada con ID: " + cliente.getIdEntidad()));

                cliente.setNombre(empresa.getRazonSocial());
                cliente.setTipoIdentificacion(empresa.getTipoIdentificacion());
                cliente.setNumeroIdentificacion(empresa.getNumeroIdentificacion());

            } else {
                throw new CrearClienteExcepcion("TipoEntidad inválido: " + cliente.getTipoEntidad());
            }

            cliente.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
            cliente.setEstado("ACTIVO");

            return clienteRepo.save(cliente);

        } catch (Exception e) {
            throw new CrearClienteExcepcion("Error al crear cliente: " + e.getMessage());
        }
    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepo.findById(id)
                .orElseThrow(() -> new ClienteNoEncontradoExcepcion(id));
    }

    public Cliente crear(Cliente cliente) {
        try {
            validarTipoEntidad(cliente);
            validarReferencias(cliente);
            
            cliente.setFechaCreacion(new Date());
            cliente.setEstado("ACTIVO");
            return clienteRepo.save(cliente);
        } catch (Exception e) {
            throw new CrearClienteExcepcion("Error al crear cliente: " + e.getMessage());
        }
    }

    private void validarReferencias(Cliente cliente) {
        // Validar País
        if (cliente.getPais() == null || cliente.getPais().getId() == null) {
            throw new CrearClienteExcepcion("El país es requerido");
        }
        Pais pais = paisRepo.findById(cliente.getPais().getId())
                .orElseThrow(() -> new CrearClienteExcepcion("País no encontrado con ID: " + cliente.getPais().getId()));
        cliente.setPais(pais);

        // Validar Sucursal
        if (cliente.getSucursal() == null || cliente.getSucursal().getIdSucursal() == null) {
            throw new CrearClienteExcepcion("La sucursal es requerida");
        }
        Sucursal sucursal = sucursalRepo.findById(cliente.getSucursal().getIdSucursal())
                .orElseThrow(() -> new CrearClienteExcepcion("Sucursal no encontrada con ID: " + cliente.getSucursal().getIdSucursal()));
        cliente.setSucursal(sucursal);
    }

    public Cliente modificar(Cliente cliente) {
        if (!clienteRepo.existsById(cliente.getIdCliente())) {
            throw new ClienteNoEncontradoExcepcion(cliente.getIdCliente());
        }
        try {
            cliente.setFechaActualizacion(new Date());
            return clienteRepo.save(cliente);
        } catch (Exception e) {
            throw new ActualizarClienteExcepcion("Error al actualizar cliente");
        }
    }

    private void validarTipoEntidad(Cliente cliente) {
        if (cliente.getTipoEntidad() == null || cliente.getIdEntidad() == null) {
            throw new CrearClienteExcepcion("TipoEntidad y IdEntidad son obligatorios");
        }

        if (cliente.getTipoEntidad().equalsIgnoreCase("PERSONA")) {
            if (!personaRepo.existsById(cliente.getIdEntidad())) {
                throw new CrearClienteExcepcion("No existe Persona con ID: " + cliente.getIdEntidad());
            }
        } else if (cliente.getTipoEntidad().equalsIgnoreCase("EMPRESA")) {
            if (!empresaRepo.existsById(cliente.getIdEntidad())) {
                throw new CrearClienteExcepcion("No existe Empresa con ID: " + cliente.getIdEntidad());
            }
        } else {
            throw new CrearClienteExcepcion("TipoEntidad inválido: debe ser PERSONA o EMPRESA");
        }
    }

    public Cliente obtenerClienteCompletoPorId(Integer id) {
        Cliente cliente = this.buscarPorId(id); // usa tu método ya existente

        if (cliente.getDirecciones() != null) {
            cliente.getDirecciones().removeIf(d -> !"ACTIVO".equalsIgnoreCase(d.getEstado()));
        }

        if (cliente.getTelefonos() != null) {
            cliente.getTelefonos().removeIf(t -> !"ACTIVO".equalsIgnoreCase(t.getEstado()));
        }

        return cliente; // Incluye contacto si existe
    }

}