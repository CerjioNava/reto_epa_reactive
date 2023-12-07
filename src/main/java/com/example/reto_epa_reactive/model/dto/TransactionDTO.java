package com.example.reto_epa_reactive.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDTO {

    private String id;
    @NotNull(message = "Field [amount] is not nullable.")
    private BigDecimal amount;
    @NotNull(message = "Field [fromAccountId] is not nullable.")
    private String fromAccountId;
    @NotNull(message = "Field [toAccountId] is not nullable.")
    private String toAccountId;
    @NotNull(message = "Field [description] is not nullable.")
    private String description;

}
