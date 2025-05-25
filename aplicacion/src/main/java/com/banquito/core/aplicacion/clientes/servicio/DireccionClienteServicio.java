package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.modelo.DireccionCliente;
import com.banquito.core.aplicacion.clientes.repositorio.DireccionClienteRepositorio;
import com.banquito.core.aplicacion.clientes.excepcion.CrearDireccionExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.ActualizarDireccionExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.DireccionNoEncontradaExcepcion;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

@Service
public class DireccionClienteServicio {

    private final DireccionClienteRepositorio direccionRepositorio;

    public DireccionClienteServicio(DireccionClienteRepositorio direccionRepositorio) {
        this.direccionRepositorio = direccionRepositorio;
    }

    public DireccionCliente buscarPorId(Integer id) {
        return direccionRepositorio.findById(id)
                .orElseThrow(() -> new DireccionNoEncontradaExcepcion(id));
    }

    public List<DireccionCliente> buscarTodos() {
        return direccionRepositorio.findAll();
    }

    public DireccionCliente crear(DireccionCliente direccion) {
        try {
            direccion.setFechaCreacion(new Date());
            return direccionRepositorio.save(direccion);
        } catch (Exception e) {
            throw new CrearDireccionExcepcion("Error al crear dirección");
        }
    }

    public DireccionCliente modificar(DireccionCliente direccion) {
        if (!direccionRepositorio.existsById(direccion.getIdDireccion())) {
            throw new DireccionNoEncontradaExcepcion(direccion.getIdDireccion());
        }
        try {
            direccion.setFechaActualizacion(new Date());
            return direccionRepositorio.save(direccion);
        } catch (Exception e) {
            throw new ActualizarDireccionExcepcion("Error al actualizar dirección");
        }
    }

    @Transactional
    public void eliminarLogicamente(Integer id) {
        DireccionCliente direccion = buscarPorId(id);
        direccion.setEstado("INACTIVO");
        direccion.setFechaActualizacion(new Date());
        direccionRepositorio.save(direccion);
    }

}
