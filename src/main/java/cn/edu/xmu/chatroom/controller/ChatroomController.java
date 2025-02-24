package cn.edu.xmu.chatroom.controller;

import cn.edu.xmu.chatroom.util.SseEmitterServer;
import cn.edu.xmu.chatroom.model.ChatRoom;
import cn.edu.xmu.chatroom.model.User;
import cn.edu.xmu.chatroom.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Optional;


@RestController
//todo need @CrossOrigin?
public class ChatroomController {
    ChatRoom chatRoom = new ChatRoom();
    @Autowired
    private ChatService chatService;

    @GetMapping("/test/send")
    Object testSend(@RequestParam String uname) {
        SseEmitterServer.sendMessage(uname, "hello test");
        return "success";
    }

    @GetMapping("/user/list")
    Object listUsers() {
        return new ArrayList<>(chatRoom.getUserMap().keySet());
    }



    @PostMapping("/user/connect")
    void connectWithUser(@RequestParam String sender, @RequestParam String receiver) {

    }

    @GetMapping("/sse/connect")
    SseEmitter connect(@RequestParam String uname) {
        Optional<User> user = Optional.ofNullable(chatRoom.getUserMap().get(uname));
        if (user.isEmpty()) {
            chatRoom.createUser(uname);
        }
        return SseEmitterServer.connect(uname);
    }

    @PostMapping("/message/send")
    Object sendMessage(@RequestBody org.xmu.chatroom.model.Message message) {
        var userMap = chatRoom.getUserMap();
        var sender = userMap.get(message.getSender());
        sender.sendMessage(message.getContent());
        return "success";
    }

    @GetMapping("/sse/close")
    void disconnect(@RequestParam String uname) {
        chatRoom.getUserMap().remove(uname);
        SseEmitterServer.removeUser(uname);
    }
}
