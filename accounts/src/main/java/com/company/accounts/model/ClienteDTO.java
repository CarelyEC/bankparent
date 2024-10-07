package com.company.accounts.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ClienteDTO {

    private Long id;
    public String identificacion;
    public String nombre;
    public Long clienteId;
    public String contrasena;
    public String estado;


}
