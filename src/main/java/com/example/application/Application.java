package com.example.application;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "hilla-kafka")
@PWA(name = "hilla-kafka", shortName = "hilla-kafka", offlineResources = {})
@Configuration
public class Application implements AppShellConfigurator {

    @Value("${topic.name}")
    private String topicName;

    @Bean
    public NewTopic createTopic()
    {
          return TopicBuilder.name(topicName).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
