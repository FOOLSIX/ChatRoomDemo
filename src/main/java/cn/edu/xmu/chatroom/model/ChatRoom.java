package cn.edu.xmu.chatroom.model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class ChatRoom {
    private Map<String, User> userMap = new ConcurrentHashMap<>();
    private static Logger logger = LoggerFactory.getLogger(ChatRoom.class);

    public void createUser(String uname) {
        userMap.put(uname, new User(uname));
    }
}
