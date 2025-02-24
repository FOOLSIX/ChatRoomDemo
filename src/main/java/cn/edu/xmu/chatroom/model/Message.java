package cn.edu.xmu.chatroom.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@Data
@AllArgsConstructor
public class Message {
    String sender;
    String receiver;
    String content;

    public String getDetailMessage() {
        return String.format("[%tF %<tT]收到自%s的消息:%s", new Date(), sender, content);
    }
}
