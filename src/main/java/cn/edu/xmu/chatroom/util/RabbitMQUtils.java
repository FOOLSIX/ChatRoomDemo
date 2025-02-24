package cn.edu.xmu.chatroom.util;

import cn.edu.xmu.chatroom.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQUtils {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitAdmin rabbitAdmin;

    private static final String DIRECT_EXCHANGE = "direct_exchange";
    private static final String DIRECT_ROUTING = "direct_message";

    public void sendMessage(Message message) {
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_ROUTING, message);
    }

    @RabbitListener(queues = "direct_queue")
    public void receiveMessage(Message message) {
        SseEmitterServer.sendMessage(message.getReceiver(), message.getDetailMessage());
    }
}
