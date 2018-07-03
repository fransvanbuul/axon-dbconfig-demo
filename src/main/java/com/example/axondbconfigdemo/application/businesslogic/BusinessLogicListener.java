package com.example.axondbconfigdemo.application.businesslogic;

import com.example.axondbconfigdemo.application.commandside.CommandB;
import com.example.axondbconfigdemo.application.commandside.EventA;
import com.example.axondbconfigdemo.application.commandside.EventB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BusinessLogicListener {

    private final CommandGateway commandGateway;

    @EventHandler
    public void on(EventA event) {
        log.debug("Processing event in stand alone event handler (business logic listener): {}", event);
        CommandB command = new CommandB(event.getAggregate1Id());
        log.debug("Sending command {}", command);
        commandGateway.send(command);
    }

    @EventHandler
    public void on(EventB event) {
        log.debug("Processing event in stand alone event handler (business logic listener): {}", event);
        CommandB command = new CommandB(event.getAggregate1Id());
        log.debug("Sending command {}", command);
        commandGateway.send(command);
    }
}
