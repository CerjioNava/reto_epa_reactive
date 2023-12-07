package com.example.reto_epa_reactive.route;

import com.example.reto_epa_reactive.model.Client;
import com.example.reto_epa_reactive.model.dto.ClientDTO;
import com.example.reto_epa_reactive.usecase.CreateClientUseCase;
import com.example.reto_epa_reactive.usecase.GetAllClientsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ClientRouter {

    @Bean
    public RouterFunction<ServerResponse> getAllClients(GetAllClientsUseCase getAllClientsUseCase) {
        return route(
                GET("client/all"),
                request ->
                        ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllClientsUseCase.get(), ClientDTO.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> createClient(CreateClientUseCase createClientUseCase) {
        Function<ClientDTO, Mono<ServerResponse>> executor =
                clientDTO ->
                        createClientUseCase.apply(clientDTO)
                        .flatMap(result ->
                                ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result));

        return route(
                POST("client/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ClientDTO.class).flatMap(executor)
        );
    }
}
