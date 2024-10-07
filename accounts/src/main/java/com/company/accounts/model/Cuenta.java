package com.company.accounts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cuentas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @Column(unique = true, nullable = false)
    private String numeroCuenta;

    @Column(nullable = false)
    private String tipoCuenta;

    private double saldo;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private String estado;

    @OneToMany(mappedBy = "cuenta")
    @JsonIgnore
    private List<Movimientos> movimientos;
}
