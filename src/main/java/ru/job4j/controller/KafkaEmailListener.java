package ru.job4j.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.job4j.model.Post;

@Component
public class KafkaEmailListener {

    @Autowired
    private KafkaTemplate<Integer, String> template;

    /**
     * Получаем Post от 'mailingList_service' и выводим его в консоль
     * @param record Post
     */
    @KafkaListener(topics = {"email"})
    public void receivingText(ConsumerRecord<Integer, Post> record) {
        System.out.println(record.partition());
        System.out.println(record.key());
        System.out.println(record.value());
    }

    /**
     * Получаем список просроченных паспортов от 'mailingList_service'
     * @param record Post
     */
    @KafkaListener(topics = {"unavailable_passports"})
    public void receivingUnavailablePassports(ConsumerRecord<Integer, Post> record) {
        System.out.println(record.partition());
        System.out.println(record.key());
        System.out.println(record.value());
    }
}
