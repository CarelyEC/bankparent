package com.company.accounts.service;

import com.company.accounts.model.ClienteDTO;
import com.company.accounts.repository.ClienteWebClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ClienteWebViewImpl implements ClienteWebClient {

    private final WebClient webClient;

    private final String serviceBASE = "/clientes";

    public ClienteWebViewImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<ClienteDTO> getAllClientes() {
        return (List<ClienteDTO>) webClient
                .get()
                .uri(serviceBASE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
    }

    @Override
    public Mono<ClienteDTO> getClienteById(Long Id) {
        return null;
    }

    @Override
    public Mono<ClienteDTO> getClienteByIdentification(String identification) {
        return webClient.get()
                .uri(serviceBASE + "/identificacion/{identificacion}", identification)
                .retrieve()
                .bodyToMono(ClienteDTO.class);
    }


}
