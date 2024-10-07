package com.company.accounts.service;

import com.company.accounts.model.Cuenta;
import com.company.accounts.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    /**
     * Listar todas las cuentas registradas de los clientes
     * @return
     */
    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    /**
     * Busqueda de Cuenta Individual por Id
     * @param numeroCuenta
     * @return
     */
    public Optional<Cuenta> getCuentaById(String numeroCuenta) {
        return cuentaRepository.findById(numeroCuenta);
    }

    public Cuenta createCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Cuenta updateCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public void deleteCuenta(String numeroCuenta) {
        cuentaRepository.deleteById(numeroCuenta);
    }
}
