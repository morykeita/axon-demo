package com.example.eventsourcingaxon.entities.repositories;

import com.example.eventsourcingaxon.entities.AccountQueryEntity;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<AccountQueryEntity,String> {
}
