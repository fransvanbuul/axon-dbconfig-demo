package com.example.axondbconfigdemo.application.commandside;

import lombok.Value;

import java.util.UUID;

@Value
public class EventB {

    UUID aggregate1Id;
    Aggregate1.Status nextStatus;

}
