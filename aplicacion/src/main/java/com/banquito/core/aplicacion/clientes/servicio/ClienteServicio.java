package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.clientes.repositorio.ClienteRepositorio;
import com.banquito.core.aplicacion.clientes.excepcion.ClienteNoEncontradoExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.CrearClienteExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.ActualizarClienteExcepcion;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServicio {

    private final ClienteRepositorio clienteRepo;

    public ClienteServicio(ClienteRepositorio clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    public Cliente crearCliente(Cliente cliente) {
        try {
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
