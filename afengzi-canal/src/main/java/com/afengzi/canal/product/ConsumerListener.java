package com.afengzi.canal.product;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;

import java.net.InetSocketAddress;

/**
 * Created by klov on 2015/4/9.
 */
public class ConsumerListener {
    private String address ;
    private Integer port ;
    private String destination;
    private String userName ;
    private String passWord ;

    private MessageListener messageListener ;

    public void initConnection(){
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(address,
                port), destination, userName, passWord);
    }
}
