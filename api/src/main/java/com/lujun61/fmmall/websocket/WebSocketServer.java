package com.lujun61.fmmall.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{oid}")
public class WebSocketServer {

    private static final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();


    /**
     * @description 当前端请求建立websocket连接时，会执行OnOpen注解的方法
     * @author Jun Lu
     * @date 2022-08-11 15:38:34
     */
    @OnOpen
    public void open(@PathParam("oid") String orderId, Session session) {
        sessionMap.put(orderId, session);
    }

    /**
     * @description 前端关闭⻚⾯或者主动关闭websocket连接，都会执⾏close
     * @author Jun Lu
     * @date 2022-08-11 15:38:34
     */
    @OnClose
    public void close(@PathParam("oid") String orderId){
        sessionMap.remove(orderId);
    }


    /**
     * @description 封装推送消息的方法
     * @author Jun Lu
     * @date 2022-08-11 15:49:03
     */
    public static void sendMsg(String orderId, String msg) {
        Session session = sessionMap.get(orderId);
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
