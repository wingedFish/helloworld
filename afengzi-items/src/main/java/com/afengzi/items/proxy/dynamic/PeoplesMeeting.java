package com.afengzi.items.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by winged fish on 2015/8/26.
 */
public class PeoplesMeeting {

    private ElectChairman electChairman;

    public ElectChairman getElectChairman() {
        return electChairman;
    }

    public void setElectChairman(ElectChairman electChairman) {
        this.electChairman = electChairman;
    }

    public Boolean vote() {
        String executorName = "root";
        return (Boolean) Proxy.newProxyInstance(ElectChairman.class.getClassLoader(), new Class[]{ElectChairman.class}, new PeoplesMeetingInvocationHandler(executorName));
    }

    class PeoplesMeetingInvocationHandler implements InvocationHandler {

        private String executorName;

        public PeoplesMeetingInvocationHandler(String executorName) {
            this.executorName = executorName;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if ("vote".equals(method.getName())) {
                return vote(method);
            }

            return method.invoke(vote(method), args);
        }

        private boolean vote(Method method) {
            System.out.println("###### operation : " + method.getName());
            return getElectChairman().vote(executorName);
        }
    }
}
