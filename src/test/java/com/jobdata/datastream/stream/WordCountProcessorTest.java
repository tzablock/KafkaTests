package com.jobdata.datastream.stream;

import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.*;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class WordCountProcessorTest {
    private WordCountProcessor wordCountProcessor = new WordCountProcessor();

    @Test
    void buildPipelineShouldCountAlaAs3AndBrekAs4ForThoseWordsProvidedThisAmountOfTimes() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        wordCountProcessor.buildPipeline(streamsBuilder);
        Topology topology = streamsBuilder.build();

        try (TopologyTestDriver topologyTestDriver = new TopologyTestDriver(topology, new Properties())){
            TestInputTopic<String, String> inputTopic = topologyTestDriver.createInputTopic("stream-input-topic", new StringSerializer(), new StringSerializer());
            TestOutputTopic<String, Long> outputTopic = topologyTestDriver.createOutputTopic("stream-output-topic", new StringDeserializer(), new LongDeserializer());

            inputTopic.pipeInput("key", "Ala Ala Ala");
            inputTopic.pipeInput("key1", "Brek Brek Brek Brek");

            assertThat(outputTopic.readKeyValuesToList())
                    .containsExactly(
                            KeyValue.pair("ala", 1L),
                            KeyValue.pair("ala", 2L),
                            KeyValue.pair("ala", 3L),
                            KeyValue.pair("brek", 1L),
                            KeyValue.pair("brek", 2L),
                            KeyValue.pair("brek", 3L),
                            KeyValue.pair("brek", 4L)
                            );
        }
    }
}