package com.afengzi.concurrent.unit;


import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;

/**
 * Created by winged fish on 2015/7/28.
 */
public class AfThreadPoolExecutor extends ThreadPoolExecutor  {
    private final static Logger log = Logger.getLogger("AfThreadPoolExecutor");
    private BlockingQueue<Runnable> workQueue ;
    private boolean threadEnd ;

    public AfThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
        this.workQueue = workQueue ;
    }
    public AfThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,BlockingQueue<Runnable> workQueue,String poolName,String threadName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
        this.workQueue = workQueue ;
        super.setThreadFactory(new CzThreadFactory(poolName,threadName));
    }


    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        //print log
        //TODO t specific do worker thread
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

        CzThreadFactory(String poolName,String threadName) {
            if (StringUtils.isBlank(threadName)){
                threadName = "thread" ;
            }
            if (StringUtils.isBlank(poolName)){
                poolName="pool" ;
            }
            SecurityManager s = System.getSecurityManager();
            group = (s != null)? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = poolName+"-" +
                    poolNumber.getAndIncrement() +
                    "-"+threadName+"-";
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

    public int getFreeCount(){
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

    /**
     * �����߳����������ֵ1/2��ʾ��ִ��
     * @return
     */
    public boolean canExecute(){
//        return getActiveCount()<(getMaximumPoolSize()/2);
        return getActiveCount()==0 ;
//        return this.getFreeQueueSize()>0;
    }

}
