package com.example.reto_epa_reactive.usecase.transaction;

import com.example.reto_epa_reactive.drivenAdapter.bus.RabbitMqPublisher;
import com.example.reto_epa_reactive.drivenAdapter.repository.ITransactionRepository;
import com.example.reto_epa_reactive.mapper.TransactionMapper;
import com.example.reto_epa_reactive.model.dto.TransactionDTO;
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
public class CreateTransactionErrorUseCase implements Function<TransactionDTO, Mono<Object>> {

    @Autowired
    private RabbitMqPublisher eventBus;

    private final ITransactionRepository iTransactionRepository;
    private final TransactionMapper mapper;

    public CreateTransactionErrorUseCase(ITransactionRepository iTransactionRepository, TransactionMapper mapper) {
        this.iTransactionRepository = iTransactionRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Object> apply(TransactionDTO transactionDTO) {
        return iTransactionRepository
                .save(mapper.fromDTOtoTransactionEntity().apply(transactionDTO))
                .flatMap(transactionUpdate ->
                    Mono.error(new RuntimeException("Error transaction forced")))
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
