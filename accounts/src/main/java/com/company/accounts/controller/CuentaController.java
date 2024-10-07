package com.company.accounts.controller;

import com.company.accounts.model.Cuenta;
import com.company.accounts.model.Movimientos;
import com.company.accounts.service.CuentaService;
import com.company.accounts.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public List<Cuenta> getAllCuentas() {
        return cuentaService.getAllCuentas();
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<Cuenta> getCuentaById(@PathVariable("numeroCuenta") String numeroCuenta) {
        Optional<Cuenta> cuenta = cuentaService.getCuentaById(numeroCuenta);
        return cuenta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cuenta createCuenta(@RequestBody Cuenta cuenta) {

        Cuenta cuentaNueva = cuentaService.createCuenta(cuenta);
        Movimientos registroInicial = new Movimientos();
        registroInicial.setFecha(new Date());
        registroInicial.setSaldo(cuenta.getSaldo());
        registroInicial.setCuenta(cuentaNueva);
        registroInicial.setTipoMovimiento("Apertura");
        registroInicial.setValor(cuenta.getSaldo());

        movimientoService.createMovimiento(registroInicial);
        return cuentaNueva;
    }

    @PutMapping("/{numeroCuenta}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable("numeroCuenta") String numeroCuenta, @RequestBody Cuenta cuenta) {
        if (cuentaService.getCuentaById(numeroCuenta).isPresent()) {
            cuenta.setNumeroCuenta(numeroCuenta);

            Movimientos arqueoCuenta = new Movimientos();
            arqueoCuenta.setFecha(new Date());
            arqueoCuenta.setSaldo(cuenta.getSaldo());
            arqueoCuenta.setTipoMovimiento("Ajuste");
            arqueoCuenta.setValor(cuenta.getSaldo());

            movimientoService.updateMovimiento(arqueoCuenta);

            return ResponseEntity.ok(cuentaService.updateCuenta(cuenta));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{numeroCuenta}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable("numeroCuenta") String numeroCuenta) {

        Optional<Cuenta> cuenta = cuentaService.getCuentaById(numeroCuenta);
        if (cuenta.isPresent()) {
            movimientoService.deleteMovimientoAll(cuenta);
            cuentaService.deleteCuenta(numeroCuenta);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
