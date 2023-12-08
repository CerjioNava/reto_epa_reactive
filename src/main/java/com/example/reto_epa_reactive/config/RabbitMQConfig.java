package com.example.reto_epa_reactive.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class RabbitMQConfig {

    @Value("${QUEUE_NAME_ERROR}")
    private String QUEUE_NAME_ERROR;
    @Value("${QUEUE_CLOUDWATCH}")
    private String QUEUE_CLOUDWATCH;
    @Value("${EXCHANGE_NAME}")
    private String EXCHANGE_NAME;
    @Value("${ROUTING_KEY_ERROR}")
    private String ROUTING_KEY_ERROR;
    @Value("${ROUTING_KEY_LOGS}")
    private String ROUTING_KEY_LOGS;
//    @Value("${RABBIT_URI}")
//        public static String URI_NAME;
    @Value("${RABBIT_URI_CLOUD}")
    private String URI_NAME;

    @Bean
    public AmqpAdmin amqpAdmin() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(URI.create(URI_NAME));
        var amqpAdmin =  new RabbitAdmin(connectionFactory);

        var exchange = new TopicExchange(EXCHANGE_NAME);
        var queueError = new Queue(QUEUE_NAME_ERROR, true, false, false);
        var queueCloudwatch = new Queue(QUEUE_CLOUDWATCH, true, false, false);

        amqpAdmin.declareExchange(exchange);

        amqpAdmin.declareQueue(queueError);
        amqpAdmin.declareQueue(queueCloudwatch);

        amqpAdmin.declareBinding(BindingBuilder.bind(queueError).to(exchange).with(ROUTING_KEY_ERROR));
        amqpAdmin.declareBinding(BindingBuilder.bind(queueCloudwatch).to(exchange).with(ROUTING_KEY_LOGS));

        return amqpAdmin;
    }


    @Bean
    public ConnectionFactory connectionFactory() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.useNio();
        connectionFactory.setUri(URI_NAME);
//        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public Mono<Connection> connectionMono(@Value("spring.application.name") String name, ConnectionFactory connectionFactory)  {
        return Mono.fromCallable(() -> connectionFactory.newConnection(name)).cache();
    }

    @Bean
    public SenderOptions senderOptions(Mono<Connection> connectionMono) {
        return new SenderOptions()
                .connectionMono(connectionMono)
                .resourceManagementScheduler(Schedulers.boundedElastic());
    }

    @Bean
    public Sender sender(SenderOptions senderOptions) {
        return RabbitFlux.createSender(senderOptions);
    }


    @Bean
    public ReceiverOptions receiverOptions(Mono<Connection> connectionMono) {
        return new ReceiverOptions()
                .connectionMono(connectionMono);
    }

    @Bean
    public Receiver receiver(ReceiverOptions receiverOptions) {
        return RabbitFlux.createReceiver(receiverOptions);
    }
}
