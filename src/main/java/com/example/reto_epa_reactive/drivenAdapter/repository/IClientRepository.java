package com.example.reto_epa_reactive.drivenAdapter.repository;

import com.example.reto_epa_reactive.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository  extends ReactiveMongoRepository<Client, String> {

}
