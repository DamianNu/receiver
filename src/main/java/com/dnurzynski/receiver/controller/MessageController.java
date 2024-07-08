package com.dnurzynski.receiver.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private final RabbitTemplate rabbitTemplate;

    public MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/message")
    public String receiverMessage() {
        Object message = rabbitTemplate.receiveAndConvert("kurs");
        if (message != null) {
            return "Udało się pobrać wiadomość!" + message.toString();
        } else {
            return "Nie ma wiadomości do odczytu!";
        }
    }

    @RabbitListener(queues = "kurs")
    public void listenerMessage(String message) {
        System.out.println(message);
    }

}