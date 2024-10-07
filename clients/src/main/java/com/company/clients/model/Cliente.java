package com.company.clients.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "clientes")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private String estado;

    public Cliente(String identificacion,
                   String nombre,
                   String genero,
                   int edad,
                   String direccion,
                   String telefono,
                   String clienteid,
                   String contrasena,
                   String estado)
    {
        super();
        this.contrasena = contrasena;
        this.estado = estado;
    }
}
