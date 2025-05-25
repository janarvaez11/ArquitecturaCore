package com.banquito.core.aplicacion.general.servicio;

import com.banquito.core.aplicacion.general.excepcion.ActualizarEntidadException;
import com.banquito.core.aplicacion.general.excepcion.CrearEntidadException;
import com.banquito.core.aplicacion.general.excepcion.MonedaNoEncontradaException;
import com.banquito.core.aplicacion.general.modelo.EntidadBancaria;
import com.banquito.core.aplicacion.general.modelo.Moneda;
import com.banquito.core.aplicacion.general.repositorio.MonedaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MonedaServicio {

    private final MonedaRepositorio monedarepositorio;
    private final EntidadBancariaServicio entidadBancariaServicio;

    public MonedaServicio(MonedaRepositorio repositorio, EntidadBancariaServicio entidadBancariaServicio) {
        this.monedarepositorio = repositorio;
        this.entidadBancariaServicio = entidadBancariaServicio;
    }


    @Transactional
    public void crearMoneda(Moneda moneda) {
        try {
            this.monedarepositorio.save(moneda);
        } catch (RuntimeException rte) {
            throw new CrearEntidadException("Moneda", "Error al crear la moneda. Texto del error: " + rte.getMessage());
        }
    }

    @Transactional
    public void crearMonedaPorPais(Moneda moneda, Pais pais) {
        try {
            moneda.setPais(pais);
            this.monedarepositorio.save(moneda);
        } catch (RuntimeException rte) {
            throw new CrearEntidadException("Moneda", "Error al crear la moneda por pais. Texto del error: " + rte.getMessage());
        }
    }

    @Transactional
    public void asignarMonedaAEntidadBancaria(String idMoneda, Integer idEntidadBancaria) {
        try {
            Optional<Moneda> monedaOptional = this.monedarepositorio.findById(idMoneda);
            if (monedaOptional.isPresent()) {
                Moneda moneda = monedaOptional.get();
                EntidadBancaria entidadBancaria = entidadBancariaServicio.EncotrarporId(idEntidadBancaria);
                moneda.setEntidadBancaria(entidadBancaria);
            } else {
                throw new MonedaNoEncontradaException("La moneda con ID: " + idMoneda + " no existe");
            }
        } catch (RuntimeException rte) {
            throw new ActualizarEntidadException("Moneda", "Error al asignar la moneda a la entidad bancaria. Texto del error: " + rte.getMessage());
        }
    }
}
