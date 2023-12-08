package com.example.reto_epa_reactive.usecase.account;

import com.example.reto_epa_reactive.drivenAdapter.bus.RabbitMqPublisher;
import com.example.reto_epa_reactive.drivenAdapter.repository.IAccountRepository;
import com.example.reto_epa_reactive.mapper.AccountMapper;
import com.example.reto_epa_reactive.model.dto.AccountDTO;
import com.example.reto_epa_reactive.model.rabbit.RabbitLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.function.Function;

@Service
@Validated
public class CreateAccountUseCase implements Function<AccountDTO, Mono<AccountDTO>> {

    @Autowired
    private RabbitMqPublisher eventBus;

    private final IAccountRepository iAccountRepository;
    private final AccountMapper mapper;

    public CreateAccountUseCase(IAccountRepository iAccountRepository, AccountMapper mapper) {
        this.iAccountRepository = iAccountRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<AccountDTO> apply(AccountDTO accountDTO) {
        return iAccountRepository
                .save(mapper.fromDTOtoAccountEntity().apply(accountDTO))
                .map(newAccount -> {
                    eventBus.publishLogs(new RabbitLogDTO(
                            "Account added successfully: " + newAccount,
                            new Date()
                    ));
                    return mapper.fromAccountEntityToDTO().apply(newAccount);
                });
    }
}
