package com.afengzi.rx.io;

import com.afengzi.util.LoggerHelper;

/**
 * Created by lixiuhai on 2016/7/8.
 */
public interface SimulateRecharge {

    public String prepareFill() throws InterruptedException;

    public String findFill() throws InterruptedException;

    public String beginFill() throws InterruptedException;

    static class Factory {
        private static final SimulateRecharge INSTANCE = new SimulateRechargeImpl();

        static SimulateRecharge getInstance() {
            return INSTANCE;
        }
    }

    static class SimulateRechargeImpl implements SimulateRecharge {

        @Override
        public String prepareFill() throws InterruptedException {
            LoggerHelper.print("prepareFill.....");
            Thread.sleep(2000);
            return Thread.currentThread().getName() + "-prepareFill";
        }

        @Override
        public String findFill() throws InterruptedException {
            LoggerHelper.print("findFill.....");
            Thread.sleep(2000);
            return Thread.currentThread().getName() + "-findFill";
        }

        @Override
        public String beginFill() throws InterruptedException {
            LoggerHelper.print("beginFill.....");
            Thread.sleep(2000);
            return Thread.currentThread().getName() + "-beginFill";
        }
    }

}
