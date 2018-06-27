package com.example.axondbconfigdemo.application.testsaga;

import com.example.axondbconfigdemo.application.commandside.EventA;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

/**
 * Dummy saga, just included to make sure we test various implementations of the SagaStore.
 */
@Saga
@Slf4j
public class MySaga {

    @StartSaga
    @SagaEventHandler(associationProperty = "aggregate1Id")
    public void on(EventA event) {
        log.debug("Starting MySaga following {}", event);
    }

}
