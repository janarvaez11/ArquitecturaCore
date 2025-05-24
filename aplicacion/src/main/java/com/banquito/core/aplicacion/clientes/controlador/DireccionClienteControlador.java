package com.banquito.core.aplicacion.clientes.controlador;

import com.banquito.core.aplicacion.clientes.dto.DireccionClienteDTO;
import com.banquito.core.aplicacion.clientes.controlador.mapper.DireccionClienteMapper;
import com.banquito.core.aplicacion.clientes.modelo.DireccionCliente;
import com.banquito.core.aplicacion.clientes.servicio.DireccionClienteServicio;
import com.banquito.core.aplicacion.clientes.excepcion.NoEncontradoExcepcion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/direcciones-cliente")
public class DireccionClienteControlador {

    private final DireccionClienteServicio servicio;

    public DireccionClienteControlador(DireccionClienteServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<DireccionClienteDTO>> obtenerTodos() {
        List<DireccionClienteDTO> resultado = this.servicio.buscarTodos()
                .stream()
                .map(DireccionClienteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DireccionClienteDTO> obtenerPorId(@PathVariable("id") Integer id) {
        DireccionCliente dir = this.servicio.buscarPorId(id);
        return ResponseEntity.ok(DireccionClienteMapper.toDTO(dir));
    }

    @ExceptionHandler(NoEncontradoExcepcion.class)
    public ResponseEntity<Void> manejarNoEncontrado() {
        return ResponseEntity.notFound().build();
    }
}

