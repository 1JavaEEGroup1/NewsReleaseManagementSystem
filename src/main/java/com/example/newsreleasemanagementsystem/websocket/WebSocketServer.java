package com.example.newsreleasemanagementsystem.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Service
@Component
@ServerEndpoint("/api/websocket/{sid}")
public class WebSocketServer {
    //静态变量，用来记录当前连接数。应该设计为线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketServers = new CopyOnWriteArraySet<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void  onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        //加入set中
        webSocketServers.add(this);
        this.sid = sid;
        //在线数加1
        addOnlineCount();
        try {
            sendMessage("conn_success");
            log.info("有新窗口开始监听：" + sid + ", 在线人数为：" + getOnlineCount());
        } catch (IOException e) {
            log.error("websocket IO Exception");
        }
    }

    /**
     * 连接关闭的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketServers.remove(this);
        //在线数减1
        subOnlineCount();
        //在断开连接情况下，更新主板占用情况为释放
        log.info("释放的sid为：" + sid);
        //这里写释放的时候要处理的业务
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 受到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + sid + "的信息：" + message);
        //群发消息
        for(WebSocketServer item : webSocketServers) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    //实现服务器主动推送
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
        log.info("消息推送到窗口" + sid + ", 推送内容：" + message);

        for(WebSocketServer item : webSocketServers) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if(sid == null) {
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
    public static synchronized int getOnlineCount() {
        return WebSocketServer.onlineCount;
    }

    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketServers() {
        return webSocketServers;
    }
}
