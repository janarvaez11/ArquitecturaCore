package com.banquito.core.aplicacion.transacciones.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.transacciones.modelo.Transferencia;
import com.banquito.core.aplicacion.transacciones.modelo.TransferenciaId;

public interface TransferenciaRepositorio extends JpaRepository<Transferencia, TransferenciaId> {

}
