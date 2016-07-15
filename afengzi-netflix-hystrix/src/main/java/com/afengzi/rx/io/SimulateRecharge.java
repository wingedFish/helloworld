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
        private static final SimulateRecharge INSTANCE_TELECOM = new SimulateTelecomImpl();
        private static final SimulateRecharge INSTANCE_BJMobile = new SimulateBJMobileImpl();

        public static SimulateRecharge getInstanceTelecom() {
            return INSTANCE_TELECOM;
        }
        public static SimulateRecharge getINSTANCE_BJMobile(){
            return INSTANCE_BJMobile;
        }
    }

    static class SimulateTelecomImpl implements SimulateRecharge {

        @Override
        public String prepareFill() throws InterruptedException {
            LoggerHelper.print(Thread.currentThread().getName() +" telecom-prepareFill start.....");
//            Thread.sleep(2000);
            LoggerHelper.print(Thread.currentThread().getName() +" telecom-prepareFill end.....");
            return Thread.currentThread().getName() + "-telecom-prepareFill";
        }

        @Override
        public String findFill() throws InterruptedException {
            LoggerHelper.print(Thread.currentThread().getName() +"telecom-findFill start.....");
//            Thread.sleep(2000);
            LoggerHelper.print(Thread.currentThread().getName() +"telecom-findFill end.....");
            return Thread.currentThread().getName() + "-telecom-findFill";
        }

        @Override
        public String beginFill() throws InterruptedException {
            LoggerHelper.print(Thread.currentThread().getName() +"telecom-beginFill start.....");
//            Thread.sleep(2000);
            LoggerHelper.print(Thread.currentThread().getName() +"telecom-beginFill end.....");
            return Thread.currentThread().getName() + "-telecom-beginFill";
        }
    }

    static class SimulateBJMobileImpl implements SimulateRecharge {

        @Override
        public String prepareFill() throws InterruptedException {
            LoggerHelper.print(Thread.currentThread().getName() +"bjmobile-prepareFill start.....");
            Thread.sleep(10000);
            LoggerHelper.print(Thread.currentThread().getName() + "bjmobile-prepareFill end");
            return Thread.currentThread().getName() + "-bjmobile-prepareFill";
        }

        @Override
        public String findFill() throws InterruptedException {
            LoggerHelper.print(Thread.currentThread().getName() +"bjmobile-findFill start.....");
            Thread.sleep(10000);
            LoggerHelper.print(Thread.currentThread().getName() + "bjmobile-findFill end.....");
            return Thread.currentThread().getName() + "-bjmobile-findFill";
        }

        @Override
        public String beginFill() throws InterruptedException {
            LoggerHelper.print(Thread.currentThread().getName() +"bjmobile-beginFill start.....");
            Thread.sleep(10000);
            LoggerHelper.print(Thread.currentThread().getName() +"bjmobile-beginFill end.....");
            return Thread.currentThread().getName() + "-bjmobile-beginFill";
        }
    }

}
