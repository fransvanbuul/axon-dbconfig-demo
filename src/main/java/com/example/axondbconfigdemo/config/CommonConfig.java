package com.example.axondbconfigdemo.config;


import com.example.axondbconfigdemo.application.testsaga.MySaga;
import com.thoughtworks.xstream.XStream;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.config.SagaConfiguration;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shared configuration that applies all the time, regardless of profile.
 */
@Configuration
public class CommonConfig {

    /**
     * Use tracking event processors for event handlers.
     */
//    @Autowired
//    public void config(EventHandlingConfiguration configuration) {
//        configuration.usingTrackingProcessors();
//    }

    /**
     * Use a tracking processor for our saga
     */
//    @Bean
//    public SagaConfiguration<MySaga> mySagaSagaConfiguration() {
//        return SagaConfiguration.trackingSagaManager(MySaga.class);
//    }

    /**
     * Enable XStream security and tell XStream which types are allowed; otherwise XStream will provide a
     * security warning. (In production, most use JSON/Jackson but XStream is an easy start.)
     */
    @Autowired
    public void config(Serializer serializer) {
        if(serializer instanceof XStreamSerializer) {
            XStream xStream = ((XStreamSerializer)serializer).getXStream();
            XStream.setupDefaultSecurity(xStream);
            xStream.allowTypesByWildcard(new String[] { "com.example.axondbconfigdemo.**", "org.axonframework.**" });
        }
    }

}
