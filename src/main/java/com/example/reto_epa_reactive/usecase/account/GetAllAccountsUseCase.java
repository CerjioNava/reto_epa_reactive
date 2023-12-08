package com.example.reto_epa_reactive.usecase.account;

import com.example.reto_epa_reactive.drivenAdapter.repository.IAccountRepository;
import com.example.reto_epa_reactive.mapper.AccountMapper;
import com.example.reto_epa_reactive.model.dto.AccountDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class GetAllAccountsUseCase implements Supplier<Flux<AccountDTO>> {

    private final AccountMapper mapper;
    private final IAccountRepository iAccountRepository;

    public GetAllAccountsUseCase(AccountMapper mapper, IAccountRepository iAccountRepository) {
        this.mapper = mapper;
        this.iAccountRepository = iAccountRepository;
    }

    @Override
    public Flux<AccountDTO> get() {
        return iAccountRepository.findAll().map(account -> mapper.fromAccountEntityToDTO().apply(account));
    }
}
