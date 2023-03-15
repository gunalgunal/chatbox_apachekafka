package com.example.application.config;

import com.example.application.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class MessageDeserializer implements Deserializer<Message> {

    private static ObjectMapper objectMapper= JsonMapper.builder().findAndAddModules().build();

    @Override
    public Message deserialize(String s, byte[] bytes) {
        try{
            return objectMapper.readValue(bytes,Message.class);
        }
        catch(IOException e)
        {
          throw new SerializationException(e);
        }

    }
}
