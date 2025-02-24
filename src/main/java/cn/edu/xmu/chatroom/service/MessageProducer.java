package cn.edu.xmu.chatroom.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendPrivateMessage(String message, String topic) {
        rabbitTemplate.convertAndSend("direct_exchange", topic, message);
    }
}
