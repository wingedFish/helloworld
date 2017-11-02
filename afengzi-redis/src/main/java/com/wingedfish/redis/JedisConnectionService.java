package com.wingedfish.redis;

import com.sun.xml.internal.ws.server.sei.SEIInvokerTube;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lixiuhai on 2017/9/22.
 */
public class JedisConnectionService {

    private void initJedisPool(){
        //use JedisPoolConfig.default value
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        String host = "";
        int port = 0 ;
        Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();

        JedisCluster jedisCluster = new JedisCluster(hostAndPortSet,jedisPoolConfig);

        jedisCluster.set("klov","22");
    }

}
