package com.example.axondbconfigdemo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Postgres + JPA Axon Config
 *
 * Tested using:
 *    docker run -it -p 5432:5432 --name postgres -e POSTGRES_USER=axonframework -e POSTGRES_PASSWORD=secret postgres:9.6
 * (To examine: docker exec -it postgres psql -U axonframework)
 *
 * There's no code here - the thing that differentiates it from the H2JPAConfig is that we also have
 * a application-pg-jpa.properties that sets database props. This will trigger Spring Boot Autoconfig
 * to set up the connection to Postgres instead of using an embedded H2.
 */
@Configuration
@Profile("pg-jpa")
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class PgJpaConfig {
}
