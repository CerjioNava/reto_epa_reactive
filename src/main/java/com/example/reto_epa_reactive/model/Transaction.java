package com.example.reto_epa_reactive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("Transaction")
public class Transaction {

    @Id
    private String id;
    private BigDecimal amount;
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal taxId;
    private String description;

    public Transaction() {
    }

    public Transaction(String id, BigDecimal amount, String fromAccountId, String toAccountId, BigDecimal taxId, String description) {
        this.id = id;
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.taxId = taxId;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getTaxId() {
        return taxId;
    }

    public void setTaxId(BigDecimal taxId) {
        this.taxId = taxId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", fromAccountId='" + fromAccountId + '\'' +
                ", toAccountId='" + toAccountId + '\'' +
                ", taxId=" + taxId +
                ", description='" + description + '\'' +
                '}';
    }
}
