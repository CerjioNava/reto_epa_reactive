package com.example.reto_epa_reactive.usecase.transaction;

import com.example.reto_epa_reactive.drivenAdapter.bus.RabbitMqPublisher;
import com.example.reto_epa_reactive.drivenAdapter.repository.ITransactionRepository;
import com.example.reto_epa_reactive.mapper.TransactionMapper;
import com.example.reto_epa_reactive.model.dto.TransactionDTO;
import com.example.reto_epa_reactive.model.rabbit.RabbitLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.function.Function;

public class GetTransactionByIdUseCase implements  Function<String, Mono<TransactionDTO>> {

    @Autowired
    private RabbitMqPublisher eventBus;

    private final ITransactionRepository iTransactionRepository;
    private final TransactionMapper mapper;

    public GetTransactionByIdUseCase(ITransactionRepository iTransactionRepository, TransactionMapper mapper) {
        this.iTransactionRepository = iTransactionRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<TransactionDTO> apply(String id) {
        return iTransactionRepository.findById(id)
                .map(transaction -> {
                    eventBus.publishLogs(new RabbitLogDTO(
                            "Transaction was found successfully: " + transaction,
                            new Date()
                    ));
                    return mapper.fromTransactionEntityToDTO().apply(transaction);
                });
    }
}
