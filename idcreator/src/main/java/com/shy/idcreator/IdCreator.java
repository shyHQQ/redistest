package com.shy.idcreator;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by 29517 on 2016-6-15.
 */
public class IdCreator {
    private static final Logger logger = Logger.getLogger(IdCreator.class);
    private static JedisPool jedisPool;

    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("redis");
            JedisPoolConfig config = new JedisPoolConfig();
            //最大连接数
            config.setMaxTotal(Integer.valueOf(bundle.getString("pool.MaxTotal")));
            //最大空闲连接数
            config.setMaxIdle(Integer.valueOf(bundle.getString("pool.maxIdle")));
            //测试可用
            config.setTestOnBorrow(Boolean.valueOf(bundle.getString("pool.testOnBorrow")));
            jedisPool = new JedisPool(config, bundle.getString("redis.host"),
                    Integer.valueOf(bundle.getString("redis.port")),
                    Integer.valueOf(bundle.getString("redis.timeout")),
                    bundle.getString("redis.auth"));
        } catch (MissingResourceException e) {
            logger.warn("未找到id server配置文件");
        }
    }


    public static long getNumId() {
        long id = getId();
//        while (id <= 0) {
//            try {
//                Thread.sleep(100);
//            } catch (Exception e) {
//            }
//            logger.error("get num id is 0");
//            id = getId();
//        }
        return id;
    }

    private static long getId() {
        if (jedisPool == null) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr("_id");
        } catch (JedisConnectionException e) {
            logger.error("get num id error", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }
}
