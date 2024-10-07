package com.company.clients.controller;

import com.company.clients.exception.ErrorDetails;
import com.company.clients.exception.GlobalExceptionHandler;
import com.company.clients.model.Cliente;
import com.company.clients.repository.ClienteRepository;
import com.company.clients.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.http.HttpClient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ClienteService clienteService;

    private GlobalExceptionHandler exception;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();

    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<?> getClienteById(@PathVariable("clienteId") Long clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<?> getClienteByIdentification(@PathVariable("identificacion") String identificacion) {
        Optional<Cliente> cliente = clienteRepository.findByIdentificacion(identificacion);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));

    }

    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {

        Optional<Cliente> clienteExistente = clienteRepository.findByIdentificacion(cliente.getIdentificacion());

        if (clienteExistente.isPresent()) {
            throw new RuntimeException("Error: Ya existe un cliente con la identificación " + cliente.getIdentificacion());
        }

        Cliente clienteGuardado = clienteService.createCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteGuardado);

    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<?> updateCliente(@PathVariable("clienteId") Long clienteId, @RequestBody Cliente cliente) {
        try {
            if (clienteService.getClienteById(clienteId).isPresent()) {
                cliente.setClienteId(clienteId);
                return ResponseEntity.ok(clienteService.updateCliente(cliente));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error: Ya existe un cliente con la identificación " + cliente.getIdentificacion());

        }
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> deleteCliente(@PathVariable("clienteId") Long clienteId) {

        if (clienteService.getClienteById(clienteId).isPresent()) {
            clienteService.deleteCliente(clienteId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}