package com.example.axondbconfigdemo.application.commandside;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class CommandB {

    @TargetAggregateIdentifier UUID aggregate1Id;

}
