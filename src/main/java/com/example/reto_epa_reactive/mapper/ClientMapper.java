package com.example.reto_epa_reactive.mapper;

import com.example.reto_epa_reactive.model.Client;
import com.example.reto_epa_reactive.model.dto.ClientDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ClientMapper {

    public Function<ClientDTO, Client> fromDTOtoClientEntity() {
        return dto -> {
            Client client = new Client();
            client.setId(dto.getId());
            client.setName(dto.getName());
            return client;
        };
    }

    public Function<Client, ClientDTO> fromClientEntityToDTO() {
        return client -> {
            ClientDTO dto = new ClientDTO();
            dto.setId(client.getId());
            dto.setName(client.getName());
            return dto;
        };
    }
}
