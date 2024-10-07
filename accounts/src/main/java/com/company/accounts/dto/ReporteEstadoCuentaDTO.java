package com.company.accounts.dto;

import java.util.List;

public class ReporteEstadoCuentaDTO {
    private Long clienteId;
    private String nombreCliente;
    private List<CuentaReporteDTO> cuentas;

    public ReporteEstadoCuentaDTO(Long clienteId, String nombreCliente, List<CuentaReporteDTO> cuentas) {
        this.clienteId = clienteId;
        this.nombreCliente = nombreCliente;
        this.cuentas = cuentas;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<CuentaReporteDTO> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaReporteDTO> cuentas) {
        this.cuentas = cuentas;
    }


}