package com.example.reto_epa_reactive.drivenAdapter.repository;

import com.example.reto_epa_reactive.model.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository  extends ReactiveMongoRepository<Account, String> {

}
