package com.banquito.core.aplicacion.transacciones.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.transacciones.modelo.Cheque;

public interface ChequeRepositorio extends JpaRepository<Cheque, Integer> {

}
