package com.example.reto_epa_reactive.mapper;

import com.example.reto_epa_reactive.model.Account;
import com.example.reto_epa_reactive.model.dto.AccountDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AccountMapper {

    public Function<AccountDTO, Account> fromDTOtoAccountEntity() {
        return dto -> {
            Account account = new Account();
            account.setId(dto.getId());
            account.setBalance(dto.getBalance());
            account.setClientId(dto.getClientId());
            return account;
        };
    }

    public Function<Account, AccountDTO> fromAccountEntityToDTO() {
        return account -> {
            AccountDTO dto = new AccountDTO();
            dto.setId(account.getId());
            dto.setBalance(account.getBalance());
            dto.setClientId(account.getClientId());
            return dto;
        };
    }

}
