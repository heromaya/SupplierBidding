package com.laodyu.web.controller.websocket;

import com.laodyu.entity.ChatMessage;
import com.laodyu.entity.User;
import com.laodyu.web.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/6/4
 * @Version 1.0
 **/
@Controller
public class ChatController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handleMsg( ChatMessage chatMessage){
        chatMessage.setDate(new Date());
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getTo(),"/queue/chat",chatMessage);
    }
}
