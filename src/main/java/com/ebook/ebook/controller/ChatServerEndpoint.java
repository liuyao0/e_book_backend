package com.ebook.ebook.controller;

import com.ebook.ebook.util.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Controller
@ServerEndpoint("/chat/{username}")
public class ChatServerEndpoint {

    @OnOpen
    public void onOpen(@PathParam("username")String username, Session session)
    {
        WebSocketUtil.addAUser(username,session);
        String message="["+username+"]加入了聊天室！";
        System.out.println(message);
        WebSocketUtil.sendMessageToAllUsers(message);
    }

    @OnClose
    public void OnClose(@PathParam("username")String username, Session session)
    {
        WebSocketUtil.removeAUser(username);
        String message="["+username+"]离开了聊天室！";
        System.out.println(message);
        WebSocketUtil.sendMessageToAllUsers(message);
        try {
            session.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(@PathParam("username") String username, String message)
    {
        message="["+username+"]:"+message;
        System.out.println(message);
        WebSocketUtil.sendMessageToAllUsers(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
