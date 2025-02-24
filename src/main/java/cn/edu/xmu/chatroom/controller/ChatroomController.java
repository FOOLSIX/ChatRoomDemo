package cn.edu.xmu.chatroom.controller;

import cn.edu.xmu.chatroom.model.Message;
import cn.edu.xmu.chatroom.service.ChatService;
import cn.edu.xmu.chatroom.util.RabbitMQUtils;
import cn.edu.xmu.chatroom.util.SseEmitterServer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
public class ChatroomController {
    private final ChatService chatService;
    private final RabbitMQUtils rabbitMQUtils;

    @GetMapping("/user/list")
    Object listUsers() {
        return new ArrayList<>(chatService.getUserSet());
    }

    @PostMapping("/user/connect")
    Object connectWithUser(@RequestParam String sender, @RequestParam String receiver) {
        if (!chatService.getUserSet().contains(sender) || !chatService.getUserSet().contains(receiver)) {
            return "fail";
        }
        return "success";
    }

    @GetMapping("/sse/connect")
    SseEmitter connect(@RequestParam String uname) {
        if (!chatService.getUserSet().contains(uname)) {
            return chatService.createUser(uname);
        }
        return SseEmitterServer.connect(uname);
    }

    @PostMapping("/message/send")
    Object sendMessage(@RequestBody Message message) {
        rabbitMQUtils.sendMessage(message);
        return "success";
    }

    @GetMapping("/sse/close")
    void disconnect(@RequestParam String uname) {
        chatService.removeUser(uname);
    }
}
