package com.rick.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.rick.springboot.kafka.WikipediaChangesProducer;

@SpringBootApplication
public class SpringBootProducerApplication implements CommandLineRunner {
    private static final Logger LOG =
            LoggerFactory.getLogger(SpringBootProducerApplication.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(SpringBootProducerApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Autowired
    private WikipediaChangesProducer wikipediaChangesProducer;


    @Override
    public void run(String... args) throws Exception {
        LOG.info("EXECUTING : command line runner");
        wikipediaChangesProducer.publishMessage();
    }
}
