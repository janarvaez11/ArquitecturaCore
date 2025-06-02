package com.banquito.core.aplicacion.clientes.repositorio;

import com.banquito.core.aplicacion.clientes.modelo.ContactoTransaccionCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ContactoTransaccionClienteRepositorio extends JpaRepository<ContactoTransaccionCliente, Integer> {
    Optional<ContactoTransaccionCliente> findByCliente_idCliente(Integer idCliente);
}

