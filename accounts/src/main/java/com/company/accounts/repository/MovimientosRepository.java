package com.company.accounts.repository;

import com.company.accounts.model.Cuenta;
import com.company.accounts.model.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {

    List<Movimientos> findByCuenta_NumeroCuentaAndFechaBetween(String numeroCuenta, Date fechaInicio, Date fechaFin);

    void deleteMovimientosByCuenta(Optional<Cuenta> cuenta);
}