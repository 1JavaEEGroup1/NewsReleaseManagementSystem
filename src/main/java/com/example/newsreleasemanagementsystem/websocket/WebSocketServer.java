package com.example.newsreleasemanagementsystem.websocket;

import com.alibaba.fastjson.JSONObject;
import com.example.newsreleasemanagementsystem.config.SpringUtil;
import com.example.newsreleasemanagementsystem.redis.service.RedisServise;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Service
@Component
@ServerEndpoint("/api/websocket/{tid}")
public class WebSocketServer {

    private static RedisServise redisServise = SpringUtil.getBean(RedisServise.class);

    //静态变量，用来记录当前连接数。应该设计为线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketServers = new CopyOnWriteArraySet<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String tid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void  onOpen(Session session, @PathParam("tid") String tid) {

        this.session = session;
        //加入set中
        webSocketServers.add(this);
        this.tid = tid;
        //在线数加1
        addOnlineCount();
        try {
            sendMessage("conn_success");
            log.info("有新窗口开始监听：" + tid + ", 在线人数为：" + getOnlineCount());
            if(! redisServise.hasKey(tid)) {
                log.info("无离线消息");
            } else {
                sendMessage(redisServise.getSet(tid).toString());
                redisServise.delete(tid);
            }
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
        log.info("释放的sid为：" + tid);
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
        log.info("收到来自窗口" + tid + "的信息：" + message);
        if(isMessageType(message) == -1) {
            log.info("消息来源异常-----消息来源ID" + tid);
            return;
        } else if (isMessageType(message) == 0) {
            for(WebSocketServer item : webSocketServers) {
                try {
                    item.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (isMessageType(message) == 1) {
            try {
                sendInfo(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @OnError
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
    public void sendInfo(String message) throws IOException {
        Boolean sign = false;
        JSONObject jsonObject = JSONObject.parseObject(message);
        String sid = jsonObject.getString("sid");
        String content = jsonObject.getString("content");
        log.info("消息推送到窗口," + " 推送内容：" + message);

        for(WebSocketServer item : webSocketServers) {
            try {
                if (item.tid.equals(sid)) {
                    item.sendMessage(message);
                    sign = true;
                }
            } catch (IOException e) {
                continue;
            }
        }
        if(sign == false) {
            redisServise.addSet(sid, content);
        }
    }

    /**
     * 新增新闻推送
     * @param message
     * @param target
     * @throws IOException
     */
    public static void pushInfo(String message, List<String> target) throws IOException {
        for(String t : target) {
            for(WebSocketServer item : webSocketServers) {
                try {
                    if(item.tid.equals(t)) {
                        item.sendMessage(message);
                        target.remove(t);
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        if(target.size() > 0) {
            for(String t : target) {
                redisServise.addSet(t, message);
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

    /**
     * 　{　　
     *
     * 　　type:"类型"
     *
     * 　　content：“内容”
     *
     * 　　sid:"发送的目标Id"
     *
     * 　　tid:"您自己的id"
     *
     *  }
     * @param msg
     * @return
     */
    public Integer isMessageType(String msg) {
        JSONObject jsonObject =JSONObject.parseObject(msg);
        Integer type = jsonObject.getInteger("type");
        if (type == 1) {
            return 1;
        } else if (type == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketServers() {
        return webSocketServers;
    }
}
