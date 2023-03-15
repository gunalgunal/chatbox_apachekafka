package com.example.application.config;

import com.example.application.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class MessageSerializer implements Serializer<Message> {

    private static ObjectMapper objectMapper= JsonMapper.builder().findAndAddModules().build();

    @Override
    public byte[] serialize(String s, Message message) {
        try{
            return objectMapper.writeValueAsBytes(message);
        }
        catch(JsonProcessingException e)
        {
            throw new SerializationException(e);
        }

    }
}
