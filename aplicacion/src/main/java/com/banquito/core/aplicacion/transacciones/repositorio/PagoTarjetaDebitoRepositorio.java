package com.banquito.core.aplicacion.transacciones.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.transacciones.modelo.PagoTarjetaDebito;

public interface PagoTarjetaDebitoRepositorio extends JpaRepository<PagoTarjetaDebito, Integer> {

}
