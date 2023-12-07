package com.example.reto_epa_reactive.model.rabbit;

import lombok.Data;

import java.util.Date;

@Data
public class RabbitErrorDTO {

    private String statusCode;
    private String error;
    private Date date;

    public RabbitErrorDTO(String statusCode, String error, Date date) {
        this.statusCode = statusCode;
        this.error = error;
        this.date = date;
    }

}
