package com.jobdata.controller;

import com.jobdata.datastream.consumerproducer.ExampleProducerService;
import com.jobdata.datastream.manual.JobOffersConsumer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stream-management")
class StreamManagementController {
    final private JobOffersConsumer jobOffersConsumer;
    final private ExampleProducerService producerService;
    final private StreamsBuilderFactoryBean factoryBean;

    @Autowired
    public StreamManagementController(JobOffersConsumer jobOffersConsumer,
                                      ExampleProducerService producerService,
                                      StreamsBuilderFactoryBean factoryBean) {
        this.jobOffersConsumer = jobOffersConsumer;
        this.producerService = producerService;
        this.factoryBean = factoryBean;
    }

    @GetMapping("/count/{word}")
    Long getWordCount(@PathVariable String word){
        KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
        ReadOnlyKeyValueStore<String, Long> counts = kafkaStreams.store(
                StoreQueryParameters.fromNameAndType("counts", QueryableStoreTypes.keyValueStore())
        );
        return counts.get(word);
    }

    @GetMapping("/start-test-streaming") //TODO to remove
    String startStreaming(){
        jobOffersConsumer.testStream();
        return "Job started streaming.";
    }

    @GetMapping("/start-checking-test-stream") //TODO to remove
    String testStreaming(){
        jobOffersConsumer.readTestStream();
        return "Works";
    }

    @GetMapping("/test") //TODO to remove
    String test(){
        System.out.println("Test");
        return "WORK";
    }

    @PostMapping("/message")
    String sendMessage(@RequestBody String message){
        producerService.sendMessage(message);
        return "Sent message";
    }
}
