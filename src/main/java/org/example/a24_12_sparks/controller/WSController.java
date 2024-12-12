package org.example.a24_12_sparks.controller;

import org.example.a24_12_sparks.config.WebSocketConfig;
import org.example.a24_12_sparks.model.MessageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ws") // Chemin de base pour toutes les méthodes de ce contrôleur
public class WSController {

    private final List<MessageBean> messageHistory = new ArrayList<>();

    //Outils pour poster des messages
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat") // Chemin complet : /ws/chat
    public void receiveMessage(MessageBean message) {
        System.out.println("/ws/chat " + message.getPseudo() + " : " + message.getMessage());
        messageHistory.add(message);

        // Envoyer la liste des messages sur le channel
        messagingTemplate.convertAndSend(WebSocketConfig.CHANNEL_NAME , messageHistory);
    }

    //A mettre dans le controller
    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        if (WebSocketConfig.CHANNEL_NAME.equals(headerAccessor.getDestination())) {
            messagingTemplate.convertAndSend(WebSocketConfig.CHANNEL_NAME , messageHistory);
        }
    }

}
