package com.example.reto_epa_reactive.route;

import com.example.reto_epa_reactive.handler.TransactionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TransactionRouter {

    @Bean
    public RouterFunction<ServerResponse> getAllTransactions(TransactionHandler transactionHandler) {
        return route(
                GET("transaction/all"),
                transactionHandler::getAllTransactions
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getTransactionById(TransactionHandler transactionHandler) {
        return route(
                GET("transaction/{id}"),
                transactionHandler::getTransactionById
        );
    }

    @Bean
    public RouterFunction<ServerResponse> createTransaction(TransactionHandler transactionHandler) {
        return route(
                POST("transaction/create"),
                transactionHandler::createTransaction
        );
    }

    @Bean
    public RouterFunction<ServerResponse> createTransactionError(TransactionHandler transactionHandler) {
        return route(
                POST("transaction/create/error"),
                transactionHandler::createTransactionError
        );
    }
}
