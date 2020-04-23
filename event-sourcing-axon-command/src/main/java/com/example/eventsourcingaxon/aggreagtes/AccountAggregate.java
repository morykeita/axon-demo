package com.example.eventsourcingaxon.aggreagtes;


import com.example.eventsourcingaxon.commands.CreateAccountCommand;
import com.example.eventsourcingaxon.commands.CreditMoneyCommand;
import com.example.eventsourcingaxon.commands.DebitMoneyCommand;
import com.example.eventsourcingaxon.events.*;
import org.axonframework.commandhandling.CommandHandler;

import org.axonframework.eventsourcing.EventSourcingHandler;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private String id;
    private double accountBalance;
    private String currency;
    private String status;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand cmd){
        AggregateLifecycle.apply(new AccountCreatedEvent(cmd.id,cmd.accountBalance,cmd.currency));
    }

    @EventSourcingHandler
    protected void on (AccountCreatedEvent event){
        this.id = event.id;
        this.accountBalance = event.accountBalance;
        this.currency = event.currency;
        this.status = String.valueOf(Status.CREATED);

        AggregateLifecycle.apply(new AccountActivatedEvent(this.id, Status.ACTIVATED));
    }

    @EventSourcingHandler
    protected void on (AccountActivatedEvent event){
        this.status = String.valueOf(event.status);
    }

    @CommandHandler
    protected void on(CreditMoneyCommand command){
        AggregateLifecycle.apply(new MoneyCreditedEvent(command.id, command.creditAmount, command.currency));
    }

    @EventSourcingHandler
    protected void on(MoneyCreditedEvent event){

        if (this.accountBalance < 0 & (this.accountBalance + event.creditAmount) >= 0){
            AggregateLifecycle.apply(new AccountActivatedEvent(this.id, Status.ACTIVATED));
        }

        this.accountBalance += event.creditAmount;
    }

    @CommandHandler
    protected void on(DebitMoneyCommand command){
        AggregateLifecycle.apply(new MoneyDebitedEvent(command.id, command.debitAmount, command.currency));
    }

    @EventSourcingHandler
    protected void on(MoneyDebitedEvent event){

        if (this.accountBalance >= 0 & (this.accountBalance - event.debitAmount) < 0){
            AggregateLifecycle.apply(new AccountHeldEvent(this.id, Status.HOLD));
        }

        this.accountBalance -= event.debitAmount;

    }

    @EventSourcingHandler
    protected void on(AccountHeldEvent event){
        this.status = String.valueOf(event.status);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
