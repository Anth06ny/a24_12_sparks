package org.example.a24_12_sparks.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration //Création d'un fichier de configuration pour les WebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //Channel que les clients viendront écouter et dans lequel on publiera
    //Il peut y avoir plusieurs channels
    public static final String CHANNEL_NAME = "/ws/myChannel";

    // Le point de terminaison pour les connexions WebSocket (handshake)
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat-app").withSockJS();
    }

    // Configurer les channels (broker) de messages que les clients viendront écouter
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(CHANNEL_NAME);
        //Définir les préfixes des destinations
        registry.setApplicationDestinationPrefixes("/ws");
    }
}
