package com.example.reto_epa_reactive.usecase.client;

import com.example.reto_epa_reactive.drivenAdapter.repository.IClientRepository;
import com.example.reto_epa_reactive.mapper.ClientMapper;
import com.example.reto_epa_reactive.model.dto.ClientDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class GetAllClientsUseCase implements Supplier<Flux<ClientDTO>> {

    private final ClientMapper mapper;
    private final IClientRepository iClientRepository;

    public GetAllClientsUseCase(ClientMapper mapper, IClientRepository iClientRepository) {
        this.mapper = mapper;
        this.iClientRepository = iClientRepository;
    }

    @Override
    public Flux<ClientDTO> get() {
        return iClientRepository.findAll().map(client -> mapper.fromClientEntityToDTO().apply(client));
    }
}
