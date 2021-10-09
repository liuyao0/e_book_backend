package com.ebook.ebook.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Component
public class RedisUtil {
    @Autowired
    private final RedisTemplate<String,Object> redisTemplate;

    public RedisUtil(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    public boolean hasKey(String key)
    {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //============================String=============================
    public void del(String ... key){
        if(key!=null&&key.length>0)
        {
            if(key.length==1)
                redisTemplate.delete(key[0]);
            else
                redisTemplate.delete(Arrays.asList(key));
        }
    }

    public Object get(String key)
    {
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    public boolean set(String key,Object value)
    {
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public long incr(String key,long delta){
        if (delta < 0) {
            throw new RuntimeException("incr factor must be greater than zero!");
        }
        return redisTemplate.opsForValue().increment(key,delta);
    }

    public long decr(String key,long delta){
        if(delta<0)
        {
            throw new RuntimeException("decr factor must be greater than zero!");
        }
        return redisTemplate.opsForValue().increment(key,-delta);
    }

    //============================Set=============================

    public Set<Object> sGet(String key)
    {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean sHasKey(String key,Object value)
    {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key,value));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public long sSet(String key,Object ... values)
    {
        try{
            return redisTemplate.opsForSet().add(key,values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long sGetSetSize(String key){
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long setRemove(String key, Object ...values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

