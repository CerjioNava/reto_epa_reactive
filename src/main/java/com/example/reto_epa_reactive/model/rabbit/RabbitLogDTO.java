package com.example.reto_epa_reactive.model.rabbit;

import lombok.Data;

import java.util.Date;

@Data
public class RabbitLogDTO {

    private String message;
    private Date date;

    public RabbitLogDTO(String message, Date date) {
        this.message = message;
        this.date = date;
    }
}
