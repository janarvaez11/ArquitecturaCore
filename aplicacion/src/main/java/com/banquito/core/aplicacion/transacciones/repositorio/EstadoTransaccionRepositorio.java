package com.banquito.core.aplicacion.transacciones.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.transacciones.modelo.EstadoTransaccion;

public interface EstadoTransaccionRepositorio extends JpaRepository<EstadoTransaccion, String> {

}
