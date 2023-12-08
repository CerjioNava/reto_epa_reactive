package com.example.reto_epa_reactive.route;

import com.example.reto_epa_reactive.handler.AccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AccountRouter {

    @Bean
    public RouterFunction<ServerResponse> getAllAccounts(AccountHandler accountHandler) {
        return route(
                GET("account/all"),
                accountHandler::getAllAccounts
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getAccountById(AccountHandler accountHandler) {
        return route(
                GET("account/{id}"),
                accountHandler::getAccountById
        );
    }

    @Bean
    public RouterFunction<ServerResponse> createAccount(AccountHandler accountHandler) {
        return route(
                POST("account/create"),
                accountHandler::createAccount
        );
    }

    @Bean
    public RouterFunction<ServerResponse> createAccountError(AccountHandler accountHandler) {
        return route(
                POST("account/create/error"),
                accountHandler::createAccountError
        );
    }
}
