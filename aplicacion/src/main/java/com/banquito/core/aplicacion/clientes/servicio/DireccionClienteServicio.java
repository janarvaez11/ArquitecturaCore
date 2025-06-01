package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.modelo.DireccionCliente;
import com.banquito.core.aplicacion.clientes.repositorio.DireccionClienteRepositorio;
import com.banquito.core.aplicacion.clientes.repositorio.ClienteRepositorio;
import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.clientes.excepcion.DireccionNoEncontradaExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.CrearDireccionExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.ActualizarDireccionExcepcion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionClienteServicio {

    private final DireccionClienteRepositorio direccionRepo;
    private final ClienteRepositorio clienteRepo;

    public DireccionClienteServicio(DireccionClienteRepositorio direccionRepo, ClienteRepositorio clienteRepo) {
        this.direccionRepo = direccionRepo;
        this.clienteRepo = clienteRepo;
    }

    public DireccionCliente crear(Integer idCliente, DireccionCliente direccion) {
        Cliente cliente = clienteRepo.findById(idCliente)
                .orElseThrow(() -> new CrearDireccionExcepcion("Cliente no existe"));
        direccion.setCliente(cliente);
        return direccionRepo.save(direccion);
    }

    public DireccionCliente buscarPorId(Integer id) {
        return direccionRepo.findById(id)
                .orElseThrow(() -> new DireccionNoEncontradaExcepcion(id));
    }

    public DireccionCliente actualizar(DireccionCliente actualizada) {
        DireccionCliente direccion = buscarPorId(actualizada.getidDireccion());
        direccion.setLinea1(actualizada.getLinea1());
        direccion.setLinea2(actualizada.getLinea2());
        direccion.setCodigoPostal(actualizada.getCodigoPostal());
        direccion.setEstado(actualizada.getEstado());
        direccion.setFechaActualizacion(actualizada.getFechaActualizacion());
        return direccionRepo.save(direccion);
    }

    public void eliminarLogica(Integer id) {
        DireccionCliente direccion = buscarPorId(id);
        direccion.setEstado("INACTIVO");
        direccionRepo.save(direccion);
    }

    public List<DireccionCliente> obtenerPorCliente(Integer idCliente) {
        return direccionRepo.findByCliente_Id(idCliente);
    }
}
