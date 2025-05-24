package com.banquito.core.aplicacion.cuentas.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import com.banquito.core.aplicacion.cuentas.modelo.TasaSaldo;


public interface TasaSaldoRepositorio extends JpaRepository<TasaSaldo, Integer> {
    // Aquí puedes agregar métodos personalizados si es necesario

}
