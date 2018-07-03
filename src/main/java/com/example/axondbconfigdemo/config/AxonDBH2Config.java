package com.example.axondbconfigdemo.config;

import io.axoniq.axondb.client.AxonDBConfiguration;
import io.axoniq.axondb.client.axon.AxonDBEventStore;
import io.axoniq.axonhub.client.boot.EventStoreAutoConfiguration;
import io.axoniq.axonhub.client.boot.MessagingAutoConfiguration;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.serialization.Serializer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("axondb-h2")
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, EventStoreAutoConfiguration.class, MessagingAutoConfiguration.class})
public class AxonDBH2Config {

    @Bean(name = "eventBus")
    public EventStore eventStore(AxonDBConfiguration axonDBConfiguration, Serializer serializer) {
        return new AxonDBEventStore(axonDBConfiguration, serializer);
    }

    @Bean
    public AxonDBConfiguration axonDBConfiguration() {
        return new AxonDBConfiguration();
    }

}
