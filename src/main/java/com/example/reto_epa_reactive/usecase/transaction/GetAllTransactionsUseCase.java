package com.example.reto_epa_reactive.usecase.transaction;

import com.example.reto_epa_reactive.drivenAdapter.repository.ITransactionRepository;
import com.example.reto_epa_reactive.mapper.TransactionMapper;
import com.example.reto_epa_reactive.model.dto.TransactionDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class GetAllTransactionsUseCase implements Supplier<Flux<TransactionDTO>> {

    private final TransactionMapper mapper;
    private final ITransactionRepository iTransactionRepository;

    public GetAllTransactionsUseCase(TransactionMapper mapper, ITransactionRepository iTransactionRepository) {
        this.mapper = mapper;
        this.iTransactionRepository = iTransactionRepository;
    }

    @Override
    public Flux<TransactionDTO> get() {
        return iTransactionRepository.findAll().map(transaction -> mapper.fromTransactionEntityToDTO().apply(transaction));
    }
}
