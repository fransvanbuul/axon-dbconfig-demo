package com.example.axondbconfigdemo.application.commandside;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@ToString
@Slf4j
public class Aggregate1 {

    enum Status {
        STATUS_1,
        STATUS_2,
        STATUS_3,
        STATUS_4,
    }

    @AggregateIdentifier
    private UUID id;
    private String name;
    private List<Status> statusses = new ArrayList<>();

    @CommandHandler
    public Aggregate1(CommandA command) {
        log.debug("Processing command: {}", command);
        apply(new EventA(command.getAggregate1Id(), command.getName()));
    }

    @CommandHandler
    public void handle(CommandB command) {
        log.debug("Processing command: {}", command);
        log.debug("Current state of aggregate: {}", this);
        Status lastStatus = statusses.get(statusses.size() - 1);
        Status nextStatus = null;
        switch(lastStatus) {
            case STATUS_1: nextStatus = Status.STATUS_2; break;
            case STATUS_2: nextStatus = Status.STATUS_3; break;
            case STATUS_3: nextStatus = Status.STATUS_4; break;
        }
        if(nextStatus != null) {
            apply(new EventB(command.getAggregate1Id(), nextStatus));
        }
    }

    @EventSourcingHandler
    public void on(EventA event) {
        log.debug("Processing event for event sourced aggregate: {}", event);
        id = event.getAggregate1Id();
        name = event.getName();
        statusses.add(Status.STATUS_1);
    }

    @EventSourcingHandler
    public void on(EventB event) {
        log.debug("Processing event for event sourced aggregate: {}", event);
        statusses.add(event.getNextStatus());
    }
}
