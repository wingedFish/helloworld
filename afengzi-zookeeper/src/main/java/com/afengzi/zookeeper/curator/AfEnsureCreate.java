package com.afengzi.zookeeper.curator;

import com.afengzi.util.LoggerHelper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;
import sun.awt.geom.AreaOp;

/**
 * Created by lixiuhai on 2015/3/28.
 */
public class AfEnsureCreate {

    private CuratorFramework client;
    private static final String zkUrl = "192.168.147.90:2181";
    private static final String NAME_SPACE = "recharge";
    private static final String PATH = "/worker";

    public static void main(String[] args) {
        AfEnsureCreate ensureCreate = new AfEnsureCreate();
        ensureCreate.initCurator();
    }

    private void initCurator(){
        createSession();
        ensureCreate();
        updatePath("0");
        setListener();
        updatePath("1") ;
    }

    private void createSession() {
        client = CuratorFrameworkFactory.builder()
                .connectString(zkUrl)
                .namespace(NAME_SPACE)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .sessionTimeoutMs(3000)
                .build();
        client.start();
        LoggerHelper.print("client started ..");
    }

    private void ensureCreate(){
        EnsurePath ensurePath = new EnsurePath(PATH) ;
        try {
            LoggerHelper.print(PATH + " ensure creating...");
            ensurePath.ensure(client.getZookeeperClient());
            ensurePath.ensure(client.getZookeeperClient());
            LoggerHelper.print(PATH + " ensure created...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updatePath(String data){
        try {
            client.setData().forPath(PATH,data.getBytes());
            LoggerHelper.print(PATH + " data update to "+data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener(){
        NodeCache nodeCache = new NodeCache(client,PATH);
        try {
            nodeCache.start(true);
            nodeCache.getListenable().addListener(new NodeDataListener(nodeCache));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
