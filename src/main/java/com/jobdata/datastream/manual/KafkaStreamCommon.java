package com.jobdata.datastream.manual;

import com.jobdata.config.KafkaConfiguration;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.Topology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
class KafkaStreamCommon {
    private KafkaConfiguration conf;

    @Autowired
    public KafkaStreamCommon(KafkaConfiguration conf) {
        this.conf = conf;
    }

    void startStream(Topology topology){
        final CountDownLatch latch = new CountDownLatch(1);

        try (final KafkaStreams streams = new KafkaStreams(topology, conf.get_configuration())){
            Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
                @Override
                public void run() {
                    streams.close();
                    latch.countDown();
                }
            });
            streams.start();
            latch.await();
        } catch (InterruptedException e){
            System.out.println(e.getMessage());
        }



    }
}
