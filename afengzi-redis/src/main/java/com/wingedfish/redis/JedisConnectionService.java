package com.wingedfish.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by lixiuhai on 2017/9/22.
 */
public class JedisConnectionService {

    private void initJedisPool(){
        //use JedisPoolConfig.default value
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        String host = "";
        int port = 0 ;
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,host,port);

    }

}
