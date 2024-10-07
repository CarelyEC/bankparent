package com.company.accounts.service;

import com.company.accounts.model.ClienteDTO;
import com.company.accounts.dto.CuentaReporteDTO;
import com.company.accounts.dto.MovimientoDTO;
import com.company.accounts.dto.ReporteEstadoCuentaDTO;
import com.company.accounts.model.Cuenta;
import com.company.accounts.model.Movimientos;
import com.company.accounts.repository.ClienteWebClient;
import com.company.accounts.repository.CuentaRepository;
import com.company.accounts.repository.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientosRepository movimientosRepository;

    private ClienteWebClient clienteWebClient;

    public ReporteService(ClienteWebClient clienteWebClient) {
        this.clienteWebClient = clienteWebClient;
    }

    /**
     * Permite busqueda de Service Client para obtener reporte por Cliente y ranFechas
     *
     * @param clienteIdentification
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws Exception
     */
    public ReporteEstadoCuentaDTO generarReporte(String clienteIdentification, Date fechaInicio, Date fechaFin) throws Exception {

        try {
            Mono<ClienteDTO> clienteRespuesta = clienteWebClient.getClienteByIdentification(clienteIdentification);
            Collection<?> detalle = Collections.singleton(clienteRespuesta.block());
            if (detalle.isEmpty()) {
                throw new Exception("No hay detalle cliente");
            }
            String clienteDTOString = Arrays.toString(detalle.toArray());
            Pattern pattern = Pattern.compile("nombre=([\\w\\s]+), clienteId=(\\d+)");
            Matcher matcher = pattern.matcher(clienteDTOString);

            String cliente = "";
            long clienteId = 0L;
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            if (matcher.find()) {
                cliente = matcher.group(1);
                clienteId = Long.parseLong(matcher.group(2));
            }

            List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);
            if (cuentas.isEmpty()) {
                throw new Exception("No hay cuentas asociadas al cliente");
            }

            List<CuentaReporteDTO> cuentasReporte = cuentas.stream().map(cuenta -> {
                List<Movimientos> movimientos = movimientosRepository.findByCuenta_NumeroCuentaAndFechaBetween(cuenta.getNumeroCuenta(), fechaInicio, fechaFin);
                List<MovimientoDTO> movimientosDTO = movimientos.stream().map(movimiento ->
                        new MovimientoDTO(movimiento.getFecha(), movimiento.getTipoMovimiento(), movimiento.getValor(), movimiento.getSaldo())
                ).collect(Collectors.toList());

                return new CuentaReporteDTO(cuenta.getNumeroCuenta(), cuenta.getTipoCuenta(), cuenta.getSaldo(), movimientosDTO);
            }).collect(Collectors.toList());

            return new ReporteEstadoCuentaDTO(clienteId, cliente, cuentasReporte);
        }catch (Exception e){
            throw new Exception("Error al generar reporte");
        }

    }


}
