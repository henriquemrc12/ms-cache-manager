package com.open.mscachemanager.jedis;

import redis.clients.jedis.JedisPool;

public class JedisTemplate extends JedisPool {

    private static final String host = "redis";

    private static final int port = 6379;

    private static final String user = "default";

    private static final String password = "@Red123321";

    public static JedisTemplate instance;

    public JedisTemplate() {
        super(host, port, user, password);
    }

    public static synchronized JedisTemplate getInstance() {
        if (instance == null)
            instance = new JedisTemplate();

        return instance;
    }
}
