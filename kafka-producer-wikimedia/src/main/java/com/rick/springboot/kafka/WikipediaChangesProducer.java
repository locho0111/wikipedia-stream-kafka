package com.rick.springboot.kafka;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import com.rick.springboot.handler.WikipediaChangesHandler;

@Service
public class WikipediaChangesProducer {

    @Value("${spring.kafka.producer.name}")
    private String topicName;

    private KafkaTemplate<String, String> kafkaTemplate;

    public WikipediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishMessage() throws InterruptedException {
        // to read real-time stream data from wikimedia, we use event source
        BackgroundEventHandler eventHandler = new WikipediaChangesHandler(kafkaTemplate, topicName);

        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        EventSource.Builder builder = new EventSource.Builder(URI.create(url));

        BackgroundEventSource.Builder backgroundBuilder =
                new BackgroundEventSource.Builder(eventHandler, builder);

        BackgroundEventSource eventSource = backgroundBuilder.build();
        eventSource.start();

        TimeUnit.MINUTES.sleep(10);
    }
}
