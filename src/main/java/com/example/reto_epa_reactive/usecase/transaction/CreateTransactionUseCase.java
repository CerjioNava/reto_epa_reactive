package com.example.reto_epa_reactive.usecase.transaction;

import com.example.reto_epa_reactive.drivenAdapter.bus.RabbitMqPublisher;
import com.example.reto_epa_reactive.drivenAdapter.repository.ITransactionRepository;
import com.example.reto_epa_reactive.mapper.TransactionMapper;
import com.example.reto_epa_reactive.model.dto.TransactionDTO;
import com.example.reto_epa_reactive.model.rabbit.RabbitLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.function.Function;

@Service
@Validated
public class CreateTransactionUseCase implements Function<TransactionDTO, Mono<TransactionDTO>> {

    @Autowired
    private RabbitMqPublisher eventBus;

    private final ITransactionRepository iTransactionRepository;
    private final TransactionMapper mapper;

    public CreateTransactionUseCase(ITransactionRepository iTransactionRepository, TransactionMapper mapper) {
        this.iTransactionRepository = iTransactionRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<TransactionDTO> apply(TransactionDTO transactionDTO) {
        return iTransactionRepository
                .save(mapper.fromDTOtoTransactionEntity().apply(transactionDTO))
                .map(newTransaction -> {
                    eventBus.publishLogs(new RabbitLogDTO(
                            "Transaction added successfully: " + newTransaction,
                            new Date()
                    ));
                    return mapper.fromTransactionEntityToDTO().apply(newTransaction);
                });
    }
}
