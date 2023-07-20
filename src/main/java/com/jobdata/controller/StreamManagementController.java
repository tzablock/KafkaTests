package com.jobdata.controller;

import com.jobdata.datastream.ExampleProducerService;
import com.jobdata.datastream.JobOffersConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stream-management")
class StreamManagementController {
    final private JobOffersConsumer jobOffersConsumer;
    final private ExampleProducerService producerService;

    @Autowired
    public StreamManagementController(JobOffersConsumer jobOffersConsumer,
                                      ExampleProducerService producerService) {
        this.jobOffersConsumer = jobOffersConsumer;
        this.producerService = producerService;
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
