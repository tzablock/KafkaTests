package com.jobdata.config;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KafkaConfiguration {
    String applicationNameInKafka;
    String kafkaServerAddress;

    public KafkaConfiguration(@Value("${app.kafka.application-name}") String applicationNameInKafka,
                              @Value("${app.kafka.bootstrap-server}") String kafkaServerAddress) {
        this.applicationNameInKafka = applicationNameInKafka;
        this.kafkaServerAddress = kafkaServerAddress;
    }

    public Properties get_configuration(){
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationNameInKafka);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerAddress);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return props;
    }
}
