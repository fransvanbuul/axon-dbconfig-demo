package com.example.axondbconfigdemo.config;

import com.mongodb.MongoClient;
import io.axoniq.axonhub.client.boot.EventStoreAutoConfiguration;
import io.axoniq.axonhub.client.boot.MessagingAutoConfiguration;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.MongoTemplate;
import org.axonframework.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;
import org.axonframework.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * All-Mongo Axon config.
 *
 * Tested against running Mongo 3.6 inside a Docker container using
 * docker run -it -p 27017:27017 --name mongo mongo:3.6
 * (reminder for myself:
 *     delete with "docker rm mongo"
 *     examine with "docker exec -it mongo mongo")
 * We'll be running fully on Mongo, disabling other database stuff.
 */
@Configuration
@Profile("mongo")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class, EventStoreAutoConfiguration.class, MessagingAutoConfiguration.class})
public class MongoConfig {

    /* Storing events in Mongo. */
    @Bean
    public EventStorageEngine eventStorageEngine(Serializer serializer, MongoClient mongo) {
        /* Lots of stuff could be configured here additionally - just using the defaults here. */
        MongoTemplate mongoTemplate = new DefaultMongoTemplate(mongo);
        return new MongoEventStorageEngine(serializer, null, mongoTemplate, new DocumentPerEventStorageStrategy());
    }

    /* Also tracking tokens. */
    @Bean
    public TokenStore tokenStore(Serializer serializer, MongoClient mongo) {
        MongoTemplate mongoTemplate = new DefaultMongoTemplate(mongo);
        return new MongoTokenStore(mongoTemplate, serializer);
    }

    /* Also sagas. */
    @Bean
    public SagaStore sagaStore(Serializer serializer, MongoClient mongo) {
        MongoTemplate mongoTemplate = new DefaultMongoTemplate(mongo);
        return new MongoSagaStore(mongoTemplate, serializer);
    }
}
