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

import java.util.List;

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

    public Cliente actualizarCliente(Cliente actualizado) {
        try {
            Cliente cliente = buscarPorId(actualizado.getId());
            cliente.setEstado(actualizado.getEstado());
            cliente.setFechaActualizacion(actualizado.getFechaActualizacion());
            cliente.setComentarios(actualizado.getComentarios());
            cliente.setNombre(actualizado.getNombre());
            return clienteRepo.save(cliente);
        } catch (Exception e) {
            throw new ActualizarClienteExcepcion("Error al actualizar cliente: " + e.getMessage());
        }
    }

    public void eliminarClienteLogico(Integer id) {
        Cliente cliente = buscarPorId(id);
        cliente.setEstado("INACTIVO");
        clienteRepo.save(cliente);
    }

    public List<Cliente> obtenerTodos() {
        return clienteRepo.findAll();
    }

    public List<Cliente> obtenerPorEstado(String estado) {
        return clienteRepo.findByEstado(estado);
    }

    public Cliente obtenerInformacionCompleta(Integer id) {
        Cliente cliente = buscarPorId(id);
        cliente.getDirecciones().size();
        cliente.getTelefonos().size();
        cliente.getContacto();
        return cliente;
    }

    public Cliente obtenerInformacionResumida(Integer id) {
        return buscarPorId(id);
    }
}
