package com.example.reto_epa_reactive.handler;

import com.example.reto_epa_reactive.model.dto.AccountDTO;
import com.example.reto_epa_reactive.usecase.account.CreateAccountErrorUseCase;
import com.example.reto_epa_reactive.usecase.account.CreateAccountUseCase;
import com.example.reto_epa_reactive.usecase.account.GetAccountByIdUseCase;
import com.example.reto_epa_reactive.usecase.account.GetAllAccountsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AccountHandler {

    @Autowired
    private GetAllAccountsUseCase getAllAccountsUseCase;
    @Autowired
    private GetAccountByIdUseCase getAccountByIdUseCase;
    @Autowired
    private CreateAccountUseCase createAccountUseCase;
    @Autowired
    private CreateAccountErrorUseCase createAccountErrorUseCase;

    public Mono<ServerResponse> getAllAccounts (ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(getAllAccountsUseCase.get(), AccountDTO.class));
    }

    public Mono<ServerResponse> getAccountById (ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(getAccountByIdUseCase.apply(request.pathVariable("id")), AccountDTO.class));

    }

    public Mono<ServerResponse> createAccount(ServerRequest request) {
        return request.bodyToMono(AccountDTO.class)
                .flatMap(accountDTO -> createAccountUseCase.apply(accountDTO)
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result)));
    }

    public Mono<ServerResponse> createAccountError(ServerRequest request) {
        return request.bodyToMono(AccountDTO.class)
                .flatMap(accountDTO -> createAccountErrorUseCase.apply(accountDTO)
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result)));
    }

}
