package com.example.axondbconfigdemo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * H2+JPA Axon config.
 *
 * Testing against an embedded H2 database and JPA, using Axon's EmbeddedEventStore and a JpaEventStorageEngine.
 * As Mongo is also on our classpath, disabling it explicitly.
 */
@Configuration
@Profile("h2-jpa")
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class H2JpaConfig {
}
