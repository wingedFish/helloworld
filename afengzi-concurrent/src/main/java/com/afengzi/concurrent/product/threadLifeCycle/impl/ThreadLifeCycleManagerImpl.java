package com.afengzi.concurrent.product.threadLifeCycle.impl;

import com.afengzi.concurrent.product.threadLifeCycle.DelayThread;
import com.afengzi.concurrent.product.threadLifeCycle.TaskTypeEnum;
import com.afengzi.concurrent.product.threadLifeCycle.ThreadLifeCycleManager;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.concurrent.DelayQueue;

/**
 * Created by lixiuhai on 2015/9/2.
 */
public class ThreadLifeCycleManagerImpl implements ThreadLifeCycleManager, InitializingBean {
    /** only write once in initialize , so no need CurrentHashMap */
    private Map<String, DelayQueue<DelayThread>> delayQueueMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        initDelayQueueMap();
    }

    @Override
    public void register(DelayThread delayThread) {
        delayQueueMap.get(delayThread.getTaskType()).add(delayThread);
    }

    private void initDelayQueueMap() {
        DelayQueue<DelayThread> delayQueue = null;
        for (TaskTypeEnum taskTypeEnum : TaskTypeEnum.values()) {
            delayQueue = new DelayQueue<DelayThread>();
            delayQueueMap.put(String.valueOf(taskTypeEnum.getType()), delayQueue);
            initDeamonThread(delayQueue);
        }
    }

    private void initDeamonThread(DelayQueue<DelayThread> delayQueue) {
        MonitorTask task = new MonitorTask(delayQueue);
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }


    class MonitorTask extends Thread {
        private DelayQueue<DelayThread> delayQueue;

        public MonitorTask(DelayQueue<DelayThread> delayQueue) {
            this.delayQueue = delayQueue;
        }

        public void run() {
            DelayThread delayThread = delayQueue.poll();
            delayThread.suicide();
        }
    }
}
