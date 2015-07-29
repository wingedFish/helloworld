package com.afengzi.concurrent.unit.timeout;

/**
 * Created by lixiuhai on 2015/7/28.
 */
public class TimeoutThread extends Thread {

    private int timeout;
    private boolean isCancel;
    private Thread interruptableThread;

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setCancel(boolean isCancel) {

        this.isCancel = isCancel;
    }

    public TimeoutThread(int timeout, Thread interruptableThread) {
        super();
        this.timeout = timeout;
        this.interruptableThread = interruptableThread;
        setDaemon(true);
//        this.isCancel=isCancel;
    }

    public synchronized void cancel() {
        isCancel = true;
    }

    @Override
    public void run() {
        try {
            sleep(timeout);
            if (!isCancel) {
                if (!interruptableThread.isInterrupted()) {
                    if (interruptableThread.getName().equals("testPool-1-testThread-3")) {
                        interruptableThread.interrupt();
                        throw new InterruptedException(interruptableThread.getName() + " interrupted !!!");
                    }

//                    interruptableThread.sleep(2);
//                    throw new RuntimeException("time out # "+interruptableThread.getName());
//                    throw new InterruptedException("time out ");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
