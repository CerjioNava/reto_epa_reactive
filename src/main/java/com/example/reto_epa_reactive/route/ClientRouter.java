package com.example.reto_epa_reactive.route;

import com.example.reto_epa_reactive.handler.AccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ClientRouter {

    @Bean
    public RouterFunction<ServerResponse> getAllClients(AccountHandler accountHandler) {
        return route(
                GET("client/all"),
                accountHandler::getAllClients
        );
    }

    @Bean
    public RouterFunction<ServerResponse> createClient(AccountHandler accountHandler) {
        return route(
                POST("client/create"),
                accountHandler::createClient
        );
    }
}
