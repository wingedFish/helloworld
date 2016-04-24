package com.afengzi.concurrent.product.threadPoolMonitor;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lixiuhai on 2016/3/8.
 */
public class AFThreadPoolExecutor extends ThreadPoolExecutor {
    private final static Logger log = Logger.getLogger(AFThreadPoolExecutor.class);
    private BlockingQueue<Runnable> workQueue ;
    private ThreadPoolNameEnum threadPoolNameEnum ;
    private boolean threadEnd ;
    /**
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param workQueue
     * @param threadPoolNameEnum 定义枚举，可以限制描述清楚，监控使用
     */
    public AFThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,BlockingQueue<Runnable> workQueue,ThreadPoolNameEnum threadPoolNameEnum) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
        super.setThreadFactory(new CzThreadFactory(threadPoolNameEnum.getThreadPoolName()));
        this.workQueue = workQueue ;
        this.threadPoolNameEnum=threadPoolNameEnum;

    }


    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        //print log
        if (isThreshold()){
            System.err.println(String.format("thread pool %s  give an alarm ! free thread size : %d  , free work queue size : %d !",threadPoolNameEnum.getThreadPoolName(), getFreeThreadCount(),getFreeQueueSize()));
        }
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        log.info(" CzThreadPoolExecutor .r : "+r);
        threadEnd = true ;
        super.afterExecute(r, t);
        printException(r,t);
    }

    static class CzThreadFactory implements ThreadFactory {
        static final AtomicInteger poolNumber = new AtomicInteger(1);
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        CzThreadFactory(String poolName) {
            if (StringUtils.isBlank(poolName)){
                poolName="threadPool" ;
            }
            SecurityManager s = System.getSecurityManager();
            group = (s != null)? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = poolName+"-" ;
//                    poolNumber.getAndIncrement() +"-";
        }

        public Thread newThread(Runnable r) {
            log.info("CzThreadFactory namePrefix : "+namePrefix);
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    private void printException(Runnable r, Throwable t){
        if (t==null && r instanceof Future<?>){
            Future<?> f = (Future<?>) r;
            if (f.isDone()){
                try {
                    f.get() ;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    t = e.getCause();
                }catch (CancellationException ce){
                    t = ce ;
                }
            }
        }
        if (t!=null){
            log.error("thread exception ! "+t.getMessage(),t);
        }
    }

    public int getFreeThreadCount(){
        return this.getMaximumPoolSize()-this.getActiveCount() ;
    }

    public int getActiveQueueSize(){
        return workQueue.size() ;
    }

    public int getFreeQueueSize(){
        return workQueue.remainingCapacity() ;
    }

    public boolean executeFinish(){
        return threadEnd&&(workQueue.size()==0);
    }

    private boolean isThreshold(){
        log.info(Thread.currentThread().getName() + " free work queue size :" + getFreeQueueSize());
        return   getFreeThreadCount()==0 &&getFreeQueueSize() <6;
    }

    /**
     * 空闲线程数大于最大值1/2表示可执行
     * @return
     */
    public boolean canExecute(){
//        return getActiveCount()<(getMaximumPoolSize()/2);
        return getActiveCount()==0 ;
//        return this.getFreeQueueSize()>0;
    }
}
