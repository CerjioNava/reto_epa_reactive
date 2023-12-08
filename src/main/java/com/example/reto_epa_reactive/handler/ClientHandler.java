package com.example.reto_epa_reactive.handler;

import com.example.reto_epa_reactive.model.dto.ClientDTO;
import com.example.reto_epa_reactive.usecase.client.CreateClientErrorUseCase;
import com.example.reto_epa_reactive.usecase.client.CreateClientUseCase;
import com.example.reto_epa_reactive.usecase.client.GetAllClientsUseCase;
import com.example.reto_epa_reactive.usecase.client.GetClientByIdUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ClientHandler {

    @Autowired
    private GetAllClientsUseCase getAllClientsUseCase;
    @Autowired
    private CreateClientUseCase createClientUseCase;
    @Autowired
    private CreateClientErrorUseCase createClientErrorUseCase;
    @Autowired
    private GetClientByIdUseCase getClientByIdUseCase;

    public Mono<ServerResponse> getAllClients (ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(getAllClientsUseCase.get(), ClientDTO.class));
    }

    public Mono<ServerResponse> getClientById (ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(getClientByIdUseCase.apply(request.pathVariable("id")), ClientDTO.class));

    }

    public Mono<ServerResponse> createClient(ServerRequest request) {
        return request.bodyToMono(ClientDTO.class)
                .flatMap(clientDTO -> createClientUseCase.apply(clientDTO)
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result)));
    }

    public Mono<ServerResponse> createClientError(ServerRequest request) {
        return request.bodyToMono(ClientDTO.class)
                .flatMap(clientDTO -> createClientErrorUseCase.apply(clientDTO)
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result)));
    }

}
