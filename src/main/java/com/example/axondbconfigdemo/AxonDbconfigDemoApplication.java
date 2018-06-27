package com.example.axondbconfigdemo;

import com.example.axondbconfigdemo.application.commandside.CommandA;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/* Normally you would have @SpringBootApplication here - splitting it up to support multiple profiles
 * with different auto configuration set up. */
@Configuration
@ComponentScan
@RequiredArgsConstructor
@Slf4j
public class AxonDbconfigDemoApplication {

    private final CommandGateway commandGateway;

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(AxonDbconfigDemoApplication.class, args);
        ctx.getBean(AxonDbconfigDemoApplication.class).run();
        ctx.close();
    }

    public void run() throws InterruptedException {
        log.debug("****** STARTUP COMPLETED, EXECUTING SOME APPLICATION LOGIC ******");
        CommandA command = new CommandA(UUID.randomUUID(), "hello");
        log.debug("Sending command {}", command);
        commandGateway.sendAndWait(command);
        /* Give some time to async process and write logging before we shutdown. */
        Thread.sleep(500);
        System.out.println();
        log.debug("****** APPLICATION LOGIC COMPLETED, SHUTTING DOWN ******");
    }

}
