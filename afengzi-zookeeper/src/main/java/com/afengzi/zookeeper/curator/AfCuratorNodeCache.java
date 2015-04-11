package com.afengzi.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

/**
 * Created by lixiuhai on 2015/3/16.
 * 节点监听编号
 */
public class AfCuratorNodeCache {

    private CuratorFramework client;
    private static final String zkUrl = "192.168.147.88:2181";
    private static final String NAME_SPACE = "recharge";
    private static final String PATH = "/worker";

    public static void main(String[] args) {
        AfCuratorNodeCache afCuratorNodeCache = new AfCuratorNodeCache();
        afCuratorNodeCache.initCuratorFrame();
        afCuratorNodeCache.createPath();
    }

    private void initCuratorFrame() {
        client = CuratorFrameworkFactory.builder()
                .connectString(zkUrl)
                .namespace(NAME_SPACE)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .sessionTimeoutMs(3000)
                .build();
    }

    private void createPath() {
        client.start();
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(PATH, "0".getBytes());
        } catch (KeeperException.NodeExistsException exception) {
            System.out.println("节点已存在.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        NodeCache nodeCache = new NodeCache(client, PATH, false);
        try {
            nodeCache.start(true);
            nodeCache.getListenable().addListener(new NodeListener(nodeCache));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            client.setData().forPath(PATH, "1".getBytes());
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class NodeListener implements NodeCacheListener {
        private NodeCache cache;

        public NodeListener(NodeCache cache) {
            this.cache = cache;
        }

        @Override
        public void nodeChanged() throws Exception {
            System.out.println("********* : " + new String(cache.getCurrentData().getData()));
            //TODO 针对分布式应用处理
        }
    }


}
