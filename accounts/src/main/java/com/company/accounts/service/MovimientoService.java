package com.company.accounts.service;

import com.company.accounts.exception.SaldoNoDisponibleException;
import com.company.accounts.model.Cuenta;
import com.company.accounts.model.Movimientos;
import com.company.accounts.repository.CuentaRepository;
import com.company.accounts.repository.MovimientosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {


    @Autowired
    private MovimientosRepository movimientosRepository;
    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Movimientos> getAllMovimientos() {
        return movimientosRepository.findAll();
    }

    public Optional<Movimientos> getMovimientoById(Long id) {
        return movimientosRepository.findById(id);
    }

    public Movimientos createMovimiento(Movimientos movimiento) {
        return movimientosRepository.save(movimiento);
    }

    public Movimientos updateMovimiento(Movimientos movimiento) {
        return movimientosRepository.save(movimiento);
    }

    public void deleteMovimiento(Long id) {
        movimientosRepository.deleteById(id);
    }

    @Transactional
    public void deleteMovimientoAll(Optional<Cuenta> cuenta) {
        movimientosRepository.deleteMovimientosByCuenta(cuenta);
    }

    /**
     * Permite el registro de movimientos transaccionales en las cuentas de los usuarios, verificando la disponibilidad
     * de saldos para proceder, si este es permitido realiza los saldos correspondientes en los movimientos del usuario.
     *
     * @param numeroCuenta
     * @param movimiento
     * @return
     * @throws Exception
     */
    @Transactional
    public Movimientos registrarMovimiento(String numeroCuenta, Movimientos movimiento) throws Exception {

        try {
            Optional<Cuenta> cuentaOpt = cuentaRepository.findById(numeroCuenta);

            if (cuentaOpt.isEmpty()) {
                throw new Exception("Aviso: La cuenta no existe");
            }

            Cuenta cuenta = cuentaOpt.get();

            double saldo = cuenta.getSaldo() + movimiento.getValor();

            if (movimiento.getValor() < 0 && saldo < 0) {
                throw new SaldoNoDisponibleException();
            }

            cuenta.setSaldo(saldo);
            if (movimiento.getValor() < 0) {
                movimiento.setTipoMovimiento("Egreso");
            } else {
                movimiento.setTipoMovimiento("Ingreso");
            }
            movimiento.setSaldo(saldo);
            movimientosRepository.save(movimiento);
            cuentaRepository.save(cuenta);

            return movimiento;
        } catch (Exception e) {
            throw new Exception("Aviso: Registro de movimiento no permitido");
        }
    }
}
