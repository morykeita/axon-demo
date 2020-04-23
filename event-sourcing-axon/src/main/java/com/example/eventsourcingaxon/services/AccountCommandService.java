package com.example.eventsourcingaxon.services;

import com.example.eventsourcingaxon.dto.AccountCreateDTO;
import com.example.eventsourcingaxon.dto.MoneyCreditDTO;
import com.example.eventsourcingaxon.dto.MoneyDebitDTO;

import java.util.concurrent.CompletableFuture;

public interface AccountCommandService {

    public CompletableFuture<String> createAccount(AccountCreateDTO accountCreateDTO);
    public CompletableFuture<String> creditMoneyToAccount(String accountNumber, MoneyCreditDTO moneyCreditDTO);
    public CompletableFuture<String> debitMoneyFromAccount(String accountNumber, MoneyDebitDTO moneyDebitDTO);
}
