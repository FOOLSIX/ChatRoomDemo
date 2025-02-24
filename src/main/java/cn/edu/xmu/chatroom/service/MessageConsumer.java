package cn.edu.xmu.chatroom.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @RabbitListener(queues = "hello_queue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}

