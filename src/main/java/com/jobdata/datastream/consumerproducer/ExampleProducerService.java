package com.jobdata.datastream.consumerproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExampleProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String testTopic;

    @Autowired
    public ExampleProducerService(KafkaTemplate<String, String> kafkaTemplate,
                                  @Value("${test.test-topic}") String testTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.testTopic = testTopic;
    }

    public void sendMessage(String message){
        kafkaTemplate.send(testTopic, message);
    }
}
