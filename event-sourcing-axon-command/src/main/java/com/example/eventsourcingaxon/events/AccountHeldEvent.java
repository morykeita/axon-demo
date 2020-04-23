package com.example.eventsourcingaxon.events;

import com.example.eventsourcingaxon.aggreagtes.Status;

public class AccountHeldEvent extends BaseEvent<String>{

    public final Status status;

    public AccountHeldEvent(String id, Status status) {
        super(id);
        this.status = status;
    }
}
