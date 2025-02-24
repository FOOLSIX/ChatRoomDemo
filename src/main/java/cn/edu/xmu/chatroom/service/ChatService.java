package cn.edu.xmu.chatroom.service;

import cn.edu.xmu.chatroom.util.SseEmitterServer;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class ChatService {
    private static final Set<String> userSet = new ConcurrentSkipListSet<>();

    public Set<String> getUserSet() {
        return userSet;
    }

    public SseEmitter createUser(String user) {
        userSet.add(user);
        return SseEmitterServer.connect(user);
    }

    public void removeUser(String user) {
        userSet.remove(user);
        SseEmitterServer.removeUser(user);
    }
}
