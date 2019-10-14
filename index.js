client = new WebSocket("ws://127.0.0.1:8080/wss");
// 监听消息
client.onmessage = function (event) {
    console.log("onmessage(), 接收到服务器消息: " + event.data);
};
// 打开 WebSocket
client.onclose = function (event) {
    //WebSocket Status:: Socket Closed
    console.log("onclose(), Socket 已关闭!");
    client = null;
};
// 打开WebSocket
client.onopen = function (event) {
    //WebSocket Status:: Socket Open
    console.log("onopen(), Socket 连接成功!");
};
client.onerror = function (event) {
    //WebSocket Status:: Error was reported
    console.log("onerror(), Socket 发生错误!");
};