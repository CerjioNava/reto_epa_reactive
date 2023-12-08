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
public class GetAccountByIdUseCase implements  Function<String, Mono<AccountDTO>> {

    @Autowired
    private RabbitMqPublisher eventBus;

    private final IAccountRepository iAccountRepository;
    private final AccountMapper mapper;

    public GetAccountByIdUseCase(IAccountRepository iAccountRepository, AccountMapper mapper) {
        this.iAccountRepository = iAccountRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<AccountDTO> apply(String id) {
        return iAccountRepository.findById(id)
                .map(account -> {
                    eventBus.publishLogs(new RabbitLogDTO(
                            "Account was found successfully: " + account,
                            new Date()
                    ));
                    return mapper.fromAccountEntityToDTO().apply(account);
                });
    }
}
