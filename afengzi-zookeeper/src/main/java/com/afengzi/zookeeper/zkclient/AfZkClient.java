package com.afengzi.zookeeper.zkclient;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lixiuhai on 2015/3/12.
 */
public class AfZkClient implements Watcher {

    private static final String ZK_ADDRESS = "192.168.147.90:2181";
    private static final String ROOT_PATCH = "/afengzi";

    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private ZooKeeper zooKeeper = null ;
    public static void main(String[] args) {
        AfZkClient afZkClient = new AfZkClient() ;
        afZkClient.init();
    }

    public void init() {
        try {
            zooKeeper = new ZooKeeper(ZK_ADDRESS, 2, new AfZkClient());
            System.out.println("@@@@@@@@ state1 : "+zooKeeper.getState());
            countDownLatch.await();
            System.out.println("@@@@@@@@ state2 : "+zooKeeper.getState());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createPath(){
        zooKeeper.create(ROOT_PATCH,"hello".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT_SEQUENTIAL,new AfStringCallback(),"hi");
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("********** watchedEvent : " + watchedEvent);
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            countDownLatch.countDown();
        }
    }

    class AfStringCallback implements AsyncCallback.StringCallback{

        @Override
        public void processResult(int i, String s, Object o, String s1) {
            System.out.println("&&&&&&&&&&&& i : "+i+" , s : "+s+" ,s1 : "+s1);
        }
    }
}
