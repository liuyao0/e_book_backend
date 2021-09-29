package com.ebook.ebook.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebook.ebook.util.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@ServerEndpoint("/chat/{username}")
public class ChatServerEndpoint {

    @OnOpen
    public void onOpen(@PathParam("username")String username, Session session)
    {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("type","join");
        jsonObject.put("username",username);
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("type","update");
        ArrayList<String> onlineUsersUsername=WebSocketUtil.getOnlineUsersUsername();
        jsonObject1.put("usernames",JSON.toJSONString(onlineUsersUsername, SerializerFeature.BrowserCompatible));
        WebSocketUtil.addAUser(username,session);
        System.out.println("["+username+"]加入了聊天室！");
        WebSocketUtil.sendMessage(session,jsonObject1.toJSONString());
        WebSocketUtil.sendMessageToAllUsers(jsonObject.toJSONString());
    }

    @OnClose
    public void OnClose(@PathParam("username")String username, Session session)
    {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("type","exit");
        jsonObject.put("username",username);
        WebSocketUtil.removeAUser(username);
        String message="["+username+"]离开了聊天室！";
        System.out.println(message);
        WebSocketUtil.sendMessageToAllUsers(jsonObject.toJSONString());
        try {
            session.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(@PathParam("username") String username, String message)
    {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("type","message");
        message="["+username+"]:"+message;
        jsonObject.put("message",message);
        System.out.println(message);
        WebSocketUtil.sendMessageToAllUsers(jsonObject.toJSONString());
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
