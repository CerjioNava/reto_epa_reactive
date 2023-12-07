package com.example.reto_epa_reactive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("Account")
public class Account {

    @Id
    private String id;
    private BigDecimal balance;
    private String clientId;

    public Account() {
    }

    public Account(String id, BigDecimal balance, String clientId) {
        this.id = id;
        this.balance = balance;
        this.clientId = clientId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
