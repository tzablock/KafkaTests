package com.jobdata.datastream.consumerproducer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ExampleConsumerService {
    @KafkaListener(topics = "test-topic", groupId = "my-group")
    public void receiveMessage(String message){
        System.out.println("Received message: "+message);
    }
}
