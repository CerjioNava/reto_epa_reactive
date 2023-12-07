package com.example.reto_epa_reactive.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {

    private String id;
    @NotNull(message = "Field [balance] is not nullable.")
    private BigDecimal balance;
    @NotNull(message = "Field [clientId] is not nullable.")
    private String clientId;

}
