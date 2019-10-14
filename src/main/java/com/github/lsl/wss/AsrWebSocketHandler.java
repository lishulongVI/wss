package com.github.lsl.wss;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class AsrWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获取接收到的数据
        String payload = message.getPayload();

        // 向客户端发送数据
        session.sendMessage(new TextMessage("response: " + payload));
    }
}