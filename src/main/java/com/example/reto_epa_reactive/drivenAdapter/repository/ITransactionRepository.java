package com.example.reto_epa_reactive.drivenAdapter.repository;

import com.example.reto_epa_reactive.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository  extends ReactiveMongoRepository<Transaction, String> {
}
