package com.example.axondbconfigdemo.application.commandside;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@ToString
@Slf4j
public class Aggregate1 {

    @AggregateIdentifier
    private UUID id;
    private String name;

    @CommandHandler
    public Aggregate1(CommandA command) {
        log.debug("Processing command: {}", command);
        apply(new EventA(command.getAggregate1Id(), command.getName()));
    }

    @CommandHandler
    public void handle(CommandB command) {
        log.debug("Processing command: {}", command);
        log.debug("Current state of aggregate: {}", this);
    }

    @EventSourcingHandler
    public void on(EventA event) {
        log.debug("Processing event for event sourced aggregate: {}", event);
        id = event.getAggregate1Id();
        name = event.getName();
    }

}
