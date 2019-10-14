package com.github.lsl.wss;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocket
public class AutoConfiguration extends WebSocketTransportRegistration implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "wss").setAllowedOrigins("*");

        registry.addHandler(BinarySocketHandler(), "wss_b")
                .setAllowedOrigins("*");

    }



    private WebSocketHandler BinarySocketHandler() {
        return new BinaryHandler();
    }

    public WebSocketHandler myHandler() {
        return new AsrWebSocketHandler();
    }
}