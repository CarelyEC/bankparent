package com.company.accounts.controller;

import com.company.accounts.model.Cuenta;
import com.company.accounts.model.Movimientos;
import com.company.accounts.service.CuentaService;
import com.company.accounts.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {

    @Autowired
    private MovimientoService movimientosService;
    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public List<Movimientos> getAllMovimientos() {
        return movimientosService.getAllMovimientos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimientos> getMovimientoById(@PathVariable("id") Long id) {
        Optional<Movimientos> movimiento = movimientosService.getMovimientoById(id);
        return movimiento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{numeroCuenta}")
    public Movimientos createMovimiento(@PathVariable("numeroCuenta") String numeroCuenta,
                                        @RequestBody Movimientos movimiento) throws Exception {

        Optional<Cuenta> cuenta = cuentaService.getCuentaById(numeroCuenta);
        movimiento.setCuenta(cuenta.get());
        return movimientosService.registrarMovimiento(numeroCuenta, movimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimientos> updateMovimiento(@PathVariable("id") Long id, @RequestBody Movimientos movimiento) throws Exception {

        Optional<Movimientos> busquedaMovimiento = movimientosService.getMovimientoById(id);

        if (busquedaMovimiento.isPresent()) {
            movimiento.setId(id);
            movimiento.setCuenta(busquedaMovimiento.get().getCuenta());
            return ResponseEntity.ok(movimientosService.registrarMovimiento(movimiento.getCuenta().getNumeroCuenta(), movimiento));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimiento(@PathVariable("id") Long id) {
        if (movimientosService.getMovimientoById(id).isPresent()) {
            movimientosService.deleteMovimiento(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
