package com.gernad.kafka.jpa;

import com.gernad.kafka.model.EventMain;
import org.springframework.data.repository.CrudRepository;

public interface EventMainRepository extends CrudRepository<EventMain, Integer> {
}
