package com.github.lsl.wss;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.*;
import java.nio.ByteBuffer;

public class BinaryHandler extends BinaryWebSocketHandler {


    private FileOutputStream fop;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 设置二进制文本大小 7M
        session.setBinaryMessageSizeLimit(1024 * 1024 * 7);
        super.afterConnectionEstablished(session);
        Long s = System.currentTimeMillis();
        // 创建文件
        File file = new File(s.toString() + ".pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        fop = new FileOutputStream(file);

    }


    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {

        ByteBuffer payload = message.getPayload();
        // 读入文件
        this.fop.write(payload.array());
        this.fop.flush();
        session.sendMessage(new BinaryMessage("ok".getBytes()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        // 文件关闭
        this.fop.close();
    }
}
