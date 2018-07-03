package com.example.axondbconfigdemo.application.projection;

import com.example.axondbconfigdemo.application.commandside.EventA;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.DomainEventMessage;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Projection {

    @EventHandler
    public void on(DomainEventMessage<EventA> eventMessage) {
        eventMessage.get
        log.debug("XXXXXXXXXXXXXXXXX {}", eventMessage );
        log.debug("Processing event in stand alone event handler (projection): {}", eventMessage.getPayload());
    }

}
