package com.jobdata.datastream.manual;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class JobOffersConsumer {
    private final KafkaStreamCommon common;

    private String inputTopic;
    private String outputTopic;

    @Autowired
    public JobOffersConsumer(KafkaStreamCommon common,
                             @Value("${test.input-topic}") String inputTopic,
                             @Value("${test.output-topic}") String outputTopic) {
        this.common = common;
        this.inputTopic = inputTopic;
        this.outputTopic = outputTopic;
    }

    public void testStream(){ //TODO to remove
        final StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> textLines = builder.stream(inputTopic);
        textLines.foreach((s, s2) -> {
            System.out.println(s);
            System.out.println(s2);
        });

        KTable<String, Long> wordCount = textLines.flatMapValues(l -> Arrays.asList(l.toLowerCase().split(" ")))
                .groupBy((keyIgnored, word) -> word)
                .count();

        KStream<String, Long> wordCountStream = wordCount.toStream();
        wordCountStream.foreach((w,c) -> {
            System.out.println(w);
            System.out.println(c);
        });

        wordCountStream.to(outputTopic, Produced.with(Serdes.String(), Serdes.Long()));

        final Topology topology = builder.build();
        System.out.println(topology.describe());

        common.startStream(topology);
    }

    public void readTestStream(){ //TODO to remove
        StreamsBuilder builder = new StreamsBuilder();
        KStream<Object, Object> outputStream = builder.stream(outputTopic);
        outputStream.foreach((k, v) -> {
            System.out.println(k);
            System.out.println(v);
        });
    }
}