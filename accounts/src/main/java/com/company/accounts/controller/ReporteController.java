package com.company.accounts.controller;

import com.company.accounts.dto.ReporteEstadoCuentaDTO;
import com.company.accounts.service.ReporteService;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/reportes")
    public ResponseEntity<ReporteEstadoCuentaDTO>  obtenerReporte(@RequestParam("clienteId") String clienteIden, @RequestParam("fecha") String fecha) {
        try {
            String[] fechas = fecha.split(",");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date inicio = dateFormat.parse(fechas[0]);
            Date fin = dateFormat.parse(fechas[1]);

            ReporteEstadoCuentaDTO reporte = reporteService.generarReporte(clienteIden, inicio, fin);

            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
