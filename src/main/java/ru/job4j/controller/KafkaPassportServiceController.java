package ru.job4j.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.job4j.model.Post;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KafkaPassportServiceController {

    @Autowired
    private KafkaTemplate<Integer, String> template;

    private final Map<String, Integer> statistic = new ConcurrentHashMap<>();

    @KafkaListener(topics = {"email"})
    public void onApiCall(ConsumerRecord<Integer, Post> record) {
        System.out.println(record.partition());
        System.out.println(record.key());
        System.out.println(record.value());
    }
}
