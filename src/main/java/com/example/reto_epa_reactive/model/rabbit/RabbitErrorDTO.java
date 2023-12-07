package com.example.reto_epa_reactive.model.rabbit;

import lombok.Data;

import java.util.Date;

@Data
public class RabbitErrorDTO {

    private Integer statusCode;
    private String error;
    private Date date;

    public RabbitErrorDTO(Integer statusCode, String error, Date date) {
        this.statusCode = statusCode;
        this.error = error;
        this.date = date;
    }

}
