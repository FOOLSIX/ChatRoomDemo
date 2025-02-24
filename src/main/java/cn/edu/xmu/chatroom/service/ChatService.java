package cn.edu.xmu.chatroom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    private MessageConsumer messageConsumer;
    @Autowired
    private MessageProducer messageProducer;

    public void sendPrivateMessage(String message, String topic){
        messageProducer.sendPrivateMessage(message, topic);
    }

    public void receiveMessage(String message){
        System.out.println(message);
    }
}
