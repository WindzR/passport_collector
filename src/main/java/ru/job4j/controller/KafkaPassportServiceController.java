package ru.job4j.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KafkaPassportServiceController {

    @Autowired
    private KafkaTemplate<Integer, String> template;

    private final Map<String, Integer> statistic = new ConcurrentHashMap<>();

    @KafkaListener(topics = {"passport_service"})
    public void onApiCall(ConsumerRecord<Integer, String> input) {
        String value = input.value();
        statistic.put(value, statistic.getOrDefault(value, 0) + 1);
        if (value.equals("PassportServiceAction")) {
            template.send("passport_service-stats", statistic.toString());
        }
    }
}
