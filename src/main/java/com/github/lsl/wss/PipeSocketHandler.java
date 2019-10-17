package com.github.lsl.wss;

import org.springframework.util.StringUtils;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PipeSocketHandler extends BinaryWebSocketHandler {

    private PipedOutputStream output;
    private PipedInputStream input;
    private Map<String, String> map = System.getenv();


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        input.close();
        System.out.println("input 关闭");
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        super.handleBinaryMessage(session, message);

        int i = message.getPayloadLength();
        if (i == 3) {
            try {
                //文件结束
                String s = new String(message.getPayload().array());
                if (s.equals("END")) {
                    output.close();
                    System.out.println("关闭out");
                }
            } catch (Exception e) {
                System.out.println("handleBinaryMessage异常:" + e.toString());
            }

        } else {
            Thread.sleep(1000);
            System.out.println("读入文件流 ");
            System.out.println(i);
            output.write(message.getPayload().array());
        }


    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        output = new PipedOutputStream();
        input = new PipedInputStream();
        output.connect(input);
        System.out.println("绑定");

        System.out.println(this.map);

        URI uri = session.getUri();
        assert uri != null;
        String query = uri.getQuery();
        System.out.println(query);
        if (!StringUtils.isEmpty(query)) {
            Map<String, String> map = new HashMap<>();
            String[] split = query.split("&");
            for (String s : split) {
                String[] split1 = s.split("=");
                map.put(split1[0], split1[1]);
            }
            System.out.println(map);
        }


        new Thread(() -> {
            byte[] buffer = new byte[4096];

            try {
                int read;
                while ((read = input.read(buffer)) != -1) {
                    System.out.println(new String(Arrays.copyOfRange(buffer, 0, read)));
                    session.sendMessage(new BinaryMessage(Arrays.copyOfRange(buffer, 0, read)));
                }

            } catch (IOException var10) {
                System.out.println(var10);
            }
        }).start();

    }

}
