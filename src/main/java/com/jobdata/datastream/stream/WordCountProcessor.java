package com.jobdata.datastream.stream;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class WordCountProcessor {
    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<String, String> messageStream = streamsBuilder
                .stream("stream-input-topic", Consumed.with(STRING_SERDE, STRING_SERDE));
        KTable<String, Long> wordCounts = messageStream.mapValues((ValueMapper<String, String>) String::toLowerCase)
                                                       .flatMapValues(v -> Arrays.asList(v.split("\\W+")))
                                                       .groupBy((k, word) -> word, Grouped.with(STRING_SERDE, STRING_SERDE))
                                                       .count(Materialized.as("counts")); //TODO materialize as local state store (to have quick access)
        wordCounts.toStream().foreach((k,v) -> System.out.printf("%s=%s%n", k, v.toString()));
        wordCounts.toStream().to("stream-output-topic");
    }
}
