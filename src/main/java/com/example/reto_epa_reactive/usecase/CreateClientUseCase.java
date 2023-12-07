package com.example.reto_epa_reactive.usecase;

import com.example.reto_epa_reactive.drivenAdapter.bus.RabbitMqPublisher;
import com.example.reto_epa_reactive.drivenAdapter.repository.IClientRepository;
import com.example.reto_epa_reactive.mapper.ClientMapper;
import com.example.reto_epa_reactive.model.Client;
import com.example.reto_epa_reactive.model.dto.ClientDTO;
import com.example.reto_epa_reactive.model.rabbit.RabbitLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.function.Function;

@Service
@Validated
public class CreateClientUseCase implements Function<ClientDTO, Mono<ClientDTO>> {

    @Autowired
    private RabbitMqPublisher eventBus;

    private final IClientRepository iClientRepository;
    private final ClientMapper mapper;

    public CreateClientUseCase(IClientRepository iClientRepository, ClientMapper mapper) {
        this.iClientRepository = iClientRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<ClientDTO> apply(ClientDTO clientDTO) {
        return iClientRepository
                .save(mapper.fromDTOtoClientEntity().apply(clientDTO))
                .map(newClient -> {
                    eventBus.publishLogs(new RabbitLogDTO(
                            "Client added successfully: " + newClient,
                            new Date()
                    ));
                    return mapper.fromClientEntityToDTO().apply(newClient);
                });
    }
}
