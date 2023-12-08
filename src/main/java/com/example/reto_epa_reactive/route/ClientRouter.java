package com.example.reto_epa_reactive.route;

import com.example.reto_epa_reactive.handler.ClientHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ClientRouter {

    @Bean
    public RouterFunction<ServerResponse> getAllClients(ClientHandler clientHandler) {
        return route(
                GET("client/all"),
                clientHandler::getAllClients
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getClientById(ClientHandler clientHandler) {
        return route(
                GET("client/{id}"),
                clientHandler::getClientById
        );
    }

    @Bean
    public RouterFunction<ServerResponse> createClient(ClientHandler clientHandler) {
        return route(
                POST("client/create"),
                clientHandler::createClient
        );
    }

    @Bean
    public RouterFunction<ServerResponse> createClientError(ClientHandler clientHandler) {
        return route(
                POST("client/create/error"),
                clientHandler::createClientError
        );
    }
}
