package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.clientes.repositorio.ClienteRepositorio;
import com.banquito.core.aplicacion.clientes.excepcion.ClienteNoEncontradoExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.CrearClienteExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.ActualizarClienteExcepcion;
import com.banquito.core.aplicacion.clientes.repositorio.EmpresaRepositorio;
import com.banquito.core.aplicacion.clientes.repositorio.PersonaRepositorio;
import org.springframework.data.domain.PageRequest;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {

    private final ClienteRepositorio clienteRepositorio;
    private final PersonaRepositorio personaRepositorio;
    private final EmpresaRepositorio empresaRepositorio;

    public ClienteServicio(ClienteRepositorio clienteRepositorio,
            PersonaRepositorio personaRepositorio,
            EmpresaRepositorio empresaRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
        this.personaRepositorio = personaRepositorio;
        this.empresaRepositorio = empresaRepositorio;
    }

    public List<Cliente> buscarPrimeros10() {
        return clienteRepositorio.findAll(PageRequest.of(0, 10)).getContent();
    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepositorio.findById(id)
                .orElseThrow(() -> new ClienteNoEncontradoExcepcion(id));
    }

    public Cliente crear(Cliente cliente) {
        try {
            validarTipoEntidad(cliente); // ← ¡AQUÍ es donde debe usarse!
            cliente.setFechaCreacion(new Date());
            cliente.setEstado("ACTIVO");
            return clienteRepositorio.save(cliente);
        } catch (Exception e) {
            throw new CrearClienteExcepcion("Error al crear cliente");
        }
    }

    public Cliente modificar(Cliente cliente) {
        if (!clienteRepositorio.existsById(cliente.getIdCliente())) {
            throw new ClienteNoEncontradoExcepcion(cliente.getIdCliente());
        }
        try {
            cliente.setFechaActualizacion(new Date());
            return clienteRepositorio.save(cliente);
        } catch (Exception e) {
            throw new ActualizarClienteExcepcion("Error al actualizar cliente");
        }
    }

    private void validarTipoEntidad(Cliente cliente) {
        if (cliente.getTipoEntidad() == null || cliente.getIdEntidad() == null) {
            throw new CrearClienteExcepcion("TipoEntidad y IdEntidad son obligatorios");
        }

        if (cliente.getTipoEntidad().equalsIgnoreCase("PERSONA")) {
            if (!personaRepositorio.existsById(cliente.getIdEntidad())) {
                throw new CrearClienteExcepcion("No existe Persona con ID: " + cliente.getIdEntidad());
            }
        } else if (cliente.getTipoEntidad().equalsIgnoreCase("EMPRESA")) {
            if (!empresaRepositorio.existsById(cliente.getIdEntidad())) {
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
