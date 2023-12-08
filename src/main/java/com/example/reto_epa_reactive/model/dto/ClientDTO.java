package com.example.reto_epa_reactive.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientDTO {

    private String id;
    @NotNull(message = "Field [name] is not nullable.")
    private String name;

}
