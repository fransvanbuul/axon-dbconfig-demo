package com.example.axondbconfigdemo.config;

import com.mongodb.MongoClient;
import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventhandling.saga.repository.jdbc.JdbcSagaStore;
import org.axonframework.eventhandling.saga.repository.jdbc.PostgresSagaSqlSchema;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.jdbc.JdbcTokenStore;
import org.axonframework.eventhandling.tokenstore.jdbc.PostgresTokenTableFactory;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcSQLErrorCodesResolver;
import org.axonframework.eventsourcing.eventstore.jdbc.PostgresEventTableFactory;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.MongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.jdbc.SpringDataSourceConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Postgres + JDBC (no JPA) Axon Config
 *
 * Tested using:
 *    docker run -it -p 5432:5432 --name postgres -e POSTGRES_USER=axonframework -e POSTGRES_PASSWORD=secret postgres:9.6
 * (To examine: docker exec -it postgres psql -U axonframework)
 *
 */
@Configuration
@Profile("pg-jdbc")
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class})
public class PgJdbcConfig {

    @Bean
    public EventStorageEngine eventStorageEngine(Serializer serializer, DataSource dataSource, TransactionManager transactionManager) {
        return new JdbcEventStorageEngine(serializer, null, new JdbcSQLErrorCodesResolver(),
                serializer, new SpringDataSourceConnectionProvider(dataSource), transactionManager);
    }

    @Bean
    public TokenStore tokenStore(Serializer serializer, DataSource dataSource) {
        return new JdbcTokenStore(new SpringDataSourceConnectionProvider(dataSource), serializer);
    }

    @Bean
    public SagaStore sagaStore(Serializer serializer, DataSource dataSource) {
        return new JdbcSagaStore(new SpringDataSourceConnectionProvider(dataSource), new PostgresSagaSqlSchema(), serializer);
    }

    /* Creating the schema */

    @Autowired
    public void createSchema(EventStorageEngine eventStorageEngine) {
        if(eventStorageEngine instanceof JdbcEventStorageEngine) {
            ((JdbcEventStorageEngine)eventStorageEngine).createSchema(PostgresEventTableFactory.INSTANCE);
        }
    }

    @Autowired
    public void createSchema(TokenStore tokenStore) {
        if(tokenStore instanceof JdbcTokenStore) {
            ((JdbcTokenStore)tokenStore).createSchema(new PostgresTokenTableFactory());
        }
    }

    @Autowired
    public void createSchema(SagaStore sagaStore) throws SQLException {
        if(sagaStore instanceof JdbcSagaStore) {
            ((JdbcSagaStore)sagaStore).createSchema();
        }
    }
}
