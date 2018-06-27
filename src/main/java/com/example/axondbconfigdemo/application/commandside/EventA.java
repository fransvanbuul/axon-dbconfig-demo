package com.example.axondbconfigdemo.application.commandside;

import lombok.Value;

import java.util.UUID;

@Value
public class EventA {

    UUID aggregate1Id;
    String name;

}
