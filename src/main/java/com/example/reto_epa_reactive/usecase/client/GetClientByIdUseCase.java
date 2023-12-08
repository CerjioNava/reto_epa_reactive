package com.example.reto_epa_reactive.usecase.client;

import com.example.reto_epa_reactive.drivenAdapter.bus.RabbitMqPublisher;
import com.example.reto_epa_reactive.drivenAdapter.repository.IClientRepository;
import com.example.reto_epa_reactive.mapper.ClientMapper;
import com.example.reto_epa_reactive.model.dto.ClientDTO;
import com.example.reto_epa_reactive.model.rabbit.RabbitLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.function.Function;

public class GetClientByIdUseCase implements  Function<String, Mono<ClientDTO>> {

    @Autowired
    private RabbitMqPublisher eventBus;

    private final IClientRepository iClientRepository;
    private final ClientMapper mapper;

    public GetClientByIdUseCase(IClientRepository iClientRepository, ClientMapper mapper) {
        this.iClientRepository = iClientRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<ClientDTO> apply(String id) {
        return iClientRepository.findById(id)
                .map(client -> {
                    eventBus.publishLogs(new RabbitLogDTO(
                            "Client was found successfully: " + client,
                            new Date()
                    ));
                    return mapper.fromClientEntityToDTO().apply(client);
                });
    }
}
