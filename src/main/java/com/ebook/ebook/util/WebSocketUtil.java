package com.ebook.ebook.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;


public class WebSocketUtil {
    private static final Map<String, Session> usersOnline=new ConcurrentHashMap<>();

    public Map<String, Session> getUsersOnline(){
        return usersOnline;
    }

    public static void addAUser(String username,Session session)
    {
        usersOnline.put(username,session);
    }

    public static void removeAUser(String username)
    {
        usersOnline.remove(username);
    }

    public static Boolean checkUsername(String username)
    {
        return usersOnline.containsKey(username);
    }

    public static void sendMessage(Session session, String message)
    {
        if(session==null)
            return;
        final RemoteEndpoint.Basic basic=session.getBasicRemote();
        if(basic==null)
            return;
        try {
            basic.sendText(message);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized static void sendMessageToAllUsers(String message)
    {
        usersOnline.forEach((username,session)->sendMessage(session,message));
    }

    public synchronized static ArrayList<String> getOnlineUsersUsername()
    {
        ArrayList<String> arrayList=new ArrayList<>();
        usersOnline.forEach((username,session)->arrayList.add(username));
        return arrayList;
    }
}
