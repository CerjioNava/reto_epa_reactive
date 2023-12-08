package com.example.reto_epa_reactive.usecase.client;

import com.example.reto_epa_reactive.drivenAdapter.bus.RabbitMqPublisher;
import com.example.reto_epa_reactive.drivenAdapter.repository.IClientRepository;
import com.example.reto_epa_reactive.mapper.ClientMapper;
import com.example.reto_epa_reactive.model.dto.ClientDTO;
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
public class CreateClientErrorUseCase implements Function<ClientDTO, Mono<Object>> {

    @Autowired
    private RabbitMqPublisher eventBus;

    private final IClientRepository iClientRepository;
    private final ClientMapper mapper;

    public CreateClientErrorUseCase(IClientRepository iClientRepository, ClientMapper mapper) {
        this.iClientRepository = iClientRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Object> apply(ClientDTO clientDTO) {
        return iClientRepository
                .save(mapper.fromDTOtoClientEntity().apply(clientDTO))
                .flatMap(clientUpdate ->
                    Mono.error(new RuntimeException("Error client forced")))
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
