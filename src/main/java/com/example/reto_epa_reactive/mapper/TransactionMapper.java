package com.example.reto_epa_reactive.mapper;

import com.example.reto_epa_reactive.model.Transaction;
import com.example.reto_epa_reactive.model.dto.TransactionDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TransactionMapper {


    public Function<TransactionDTO, Transaction> fromDTOtoTransactionEntity() {
        return dto -> {
            Transaction transaction = new Transaction();
            transaction.setId(dto.getId());
            transaction.setAmount(dto.getAmount());
            transaction.setFromAccountId(dto.getFromAccountId());
            transaction.setToAccountId(dto.getToAccountId());
            transaction.setDescription(dto.getDescription());
            return transaction;
        };
    }

    public Function<Transaction, TransactionDTO> fromTransactionEntityToDTO() {
        return transaction -> {
            TransactionDTO dto = new TransactionDTO();
            dto.setId(transaction.getId());
            dto.setAmount(transaction.getAmount());
            dto.setFromAccountId(transaction.getFromAccountId());
            dto.setToAccountId(transaction.getToAccountId());
            dto.setDescription(transaction.getDescription());
            return dto;
        };
    }
}
