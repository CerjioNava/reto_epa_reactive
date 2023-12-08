package com.example.reto_epa_reactive.usecase.account;

import com.example.reto_epa_reactive.drivenAdapter.bus.RabbitMqPublisher;
import com.example.reto_epa_reactive.drivenAdapter.repository.IAccountRepository;
import com.example.reto_epa_reactive.mapper.AccountMapper;
import com.example.reto_epa_reactive.model.dto.AccountDTO;
import com.example.reto_epa_reactive.model.rabbit.RabbitErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.function.Function;

@Service
@Validated
public class CreateAccountErrorUseCase implements Function<AccountDTO, Mono<Object>> {

    @Autowired
    private RabbitMqPublisher eventBus;

    private final IAccountRepository iAccountRepository;
    private final AccountMapper mapper;

    public CreateAccountErrorUseCase(IAccountRepository iAccountRepository, AccountMapper mapper) {
        this.iAccountRepository = iAccountRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Object> apply(AccountDTO accountDTO) {
        return iAccountRepository
                .save(mapper.fromDTOtoAccountEntity().apply(accountDTO))
                .flatMap(accountUpdate ->
                    Mono.error(new RuntimeException("Error account forced")))
                .onErrorResume(error -> {
                    eventBus.publishMessageError(new RabbitErrorDTO(
                            HttpStatus.BAD_REQUEST.value(),
                            error.getMessage(),
                            new Date()
                    ));
                    return Mono.empty();
                });
    }
}
