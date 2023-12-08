package com.example.reto_epa_reactive.handler;

import com.example.reto_epa_reactive.model.dto.TransactionDTO;
import com.example.reto_epa_reactive.usecase.transaction.CreateTransactionErrorUseCase;
import com.example.reto_epa_reactive.usecase.transaction.CreateTransactionUseCase;
import com.example.reto_epa_reactive.usecase.transaction.GetAllTransactionsUseCase;
import com.example.reto_epa_reactive.usecase.transaction.GetTransactionByIdUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TransactionHandler {

    @Autowired
    private GetAllTransactionsUseCase getAllTransactionsUseCase;
    @Autowired
    private CreateTransactionUseCase createTransactionUseCase;
    @Autowired
    private CreateTransactionErrorUseCase createTransactionErrorUseCase;
    @Autowired
    private GetTransactionByIdUseCase getTransactionByIdUseCase;

    public Mono<ServerResponse> getAllTransactions (ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(getAllTransactionsUseCase.get(), TransactionDTO.class));
    }

    public Mono<ServerResponse> getTransactionById (ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(getTransactionByIdUseCase.apply(request.pathVariable("id")), TransactionDTO.class));

    }

    public Mono<ServerResponse> createTransaction(ServerRequest request) {
        return request.bodyToMono(TransactionDTO.class)
                .flatMap(transactionDTO -> createTransactionUseCase.apply(transactionDTO)
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result)));
    }

    public Mono<ServerResponse> createTransactionError(ServerRequest request) {
        return request.bodyToMono(TransactionDTO.class)
                .flatMap(transactionDTO -> createTransactionErrorUseCase.apply(transactionDTO)
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result)));
    }

}
