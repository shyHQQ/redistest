package com.shy.jedisdemo.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by 29517 on 2016-6-16.
 */
public class JedisDemoTest {
    private Jedis jedis;

    @Before
    public void init(){
        jedis = new Jedis(host, port, timeout);
        jedis.auth(auth);
    }

    @After
    public void close(){
        jedis.close();
    }

    @Test
    public void testStringSet(){
        //成功返回OK
        System.out.println(jedis.set("lingou", "sb"));
        System.out.println(jedis.get("lingou"));
    }

    @Test
    public void testStringMSet(){
        jedis.mset("lingou", "sb", "liaogou", "2b");
        for(String laogou : jedis.mget("lingou", "liaogou")){
            System.out.println(laogou);
        }
    }

    @Test
    public void testStringAppend(){
        //将value拼接到已有key的value后面，key不存在时同set
        jedis.append("lingou", "sb");
        System.out.println(jedis.get("lingou"));
    }

    @Test
    public void testIncr(){
        //自增，当key不存在时，返回1
        System.out.println(jedis.incr("_id"));
    }

    @Test
    public void testDelete(){
        //删除key，key存在返回1，不存在返回0
        System.out.println(jedis.del("lingou"));
    }
}
