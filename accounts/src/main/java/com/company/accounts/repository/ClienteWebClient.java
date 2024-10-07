package com.company.accounts.repository;

import com.company.accounts.model.ClienteDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ClienteWebClient  {

    List<ClienteDTO> getAllClientes();

    Mono<ClienteDTO> getClienteById(Long Id);

    public Mono<ClienteDTO> getClienteByIdentification(String identification);

}
