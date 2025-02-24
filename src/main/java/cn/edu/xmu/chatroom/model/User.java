package cn.edu.xmu.chatroom.model;

import cn.edu.xmu.chatroom.util.SseEmitterServer;
import lombok.Data;

@Data
public class User {
    private final String uname;

    public void connect(String receiverUname) {

    }

    public void connectWithGroup(String groupName) {

    }

    public void sendMessage(String content) {

    }

    public void onMessage(org.xmu.chatroom.model.Message message) {
        SseEmitterServer.sendMessage(uname, "接收到消息：" + message.getContent());
    }

    public void enterGroup(String groupName) {

    }

}
