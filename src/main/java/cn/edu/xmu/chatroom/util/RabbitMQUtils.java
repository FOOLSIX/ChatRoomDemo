package cn.edu.xmu.chatroom.util;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQUtils {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private DirectExchange directExchange;

    public void sendMessage(String queueName, String message) {
        String DIRECT_EXCHANGE = "direct.exchange";
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, queueName, message);
    }

    public void createQueueWithBinding(String queueName) {
        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue(queueName)).to(directExchange).with(queueName));
    }

    public void removeQueue(String queueName) {
        rabbitAdmin.deleteQueue(queueName);
    }
}
