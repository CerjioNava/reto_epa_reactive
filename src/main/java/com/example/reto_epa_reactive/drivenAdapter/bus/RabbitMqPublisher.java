package com.example.reto_epa_reactive.drivenAdapter.bus;

import com.example.reto_epa_reactive.config.RabbitMQConfig;
import com.example.reto_epa_reactive.model.rabbit.RabbitErrorDTO;
import com.example.reto_epa_reactive.model.rabbit.RabbitLogDTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Component
public class RabbitMqPublisher {

    @Autowired
    private Sender sender;

    @Autowired
    private Gson gson;

    public RabbitMqPublisher() {
    }

    public void publishMessageError(RabbitErrorDTO rabbitError){
        sender
                .send(Mono.just(new OutboundMessage(
                        System.getenv("EXCHANGE_NAME"),
                        System.getenv("ROUTING_KEY_ERROR"),
                        gson.toJson(rabbitError).getBytes())
                )).subscribe();
    }

    public void publishLogs(RabbitLogDTO rabbitLog){
        sender
                .send(Mono.just(new OutboundMessage(
                        System.getenv("EXCHANGE_NAME"),
                        System.getenv("ROUTING_KEY_LOGS"),
                        gson.toJson(rabbitLog).getBytes())
                )).subscribe();
    }
}
