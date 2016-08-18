package com.afengzi.zookeeper.curator;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

/**
 * Created by winged fish on 2015/3/28.
 */
public class NodeDataListener implements NodeCacheListener {

    private NodeCache cache;

    public NodeDataListener(NodeCache cache) {
        this.cache = cache;
    }
    @Override
    public void nodeChanged() throws Exception {
        System.out.println("********* : " + new String(cache.getCurrentData().getData()));
        //TODO ��Էֲ�ʽӦ�ô���
    }
}
