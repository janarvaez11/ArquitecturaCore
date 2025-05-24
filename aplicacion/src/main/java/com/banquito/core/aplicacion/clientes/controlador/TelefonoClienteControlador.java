package com.banquito.core.aplicacion.clientes.controlador;


import com.banquito.core.aplicacion.clientes.dto.TelefonoClienteDTO;
import com.banquito.core.aplicacion.clientes.controlador.mapper.TelefonoClienteMapper;
import com.banquito.core.aplicacion.clientes.modelo.TelefonoCliente;
import com.banquito.core.aplicacion.clientes.servicio.TelefonoClienteServicio;
import com.banquito.core.aplicacion.clientes.excepcion.NoEncontradoExcepcion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/telefonos-cliente")
public class TelefonoClienteControlador {

    private final TelefonoClienteServicio servicio;

    public TelefonoClienteControlador(TelefonoClienteServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<TelefonoClienteDTO>> obtenerTodos() {
        List<TelefonoClienteDTO> resultado = this.servicio.buscarTodos()
                .stream()
                .map(TelefonoClienteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelefonoClienteDTO> obtenerPorId(@PathVariable("id") Integer id) {
        TelefonoCliente tel = this.servicio.buscarPorId(id);
        return ResponseEntity.ok(TelefonoClienteMapper.toDTO(tel));
    }

    @ExceptionHandler(NoEncontradoExcepcion.class)
    public ResponseEntity<Void> manejarNoEncontrado() {
        return ResponseEntity.notFound().build();
    }
}
