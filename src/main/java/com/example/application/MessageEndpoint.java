package com.example.application;

import com.example.application.model.Message;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Instant;


@Endpoint
@AnonymousAllowed
public class MessageEndpoint {

    @Value("${topic.name}")
    private String topicName;

    private Sinks.Many<Message> chatSink;

    private  Flux<Message> chat;

    private KafkaTemplate<String,Message> kafkaTemplate;

    public MessageEndpoint(KafkaTemplate<String,Message> kafkaTemplate)
    {
        this.kafkaTemplate=kafkaTemplate;
        chatSink = Sinks.many().multicast().directBestEffort();
        chat = chatSink.asFlux();
    }

    public Flux<Message> join()
    {
        return chat;
    }

    public void send(Message message)
    {
        message.setTime(Instant.now());
        kafkaTemplate.send(topicName,message);
    }

    @KafkaListener(topics = "chat", groupId = "chat-group")
    private void consumer(Message message)
    {
        chatSink.emitNext(message,
                (signalType, emitResult) -> emitResult == Sinks.EmitResult.FAIL_NON_SERIALIZED);
    }
}
