package com.afengzi.dao;

import org.springframework.beans.factory.InitializingBean;

/**
 * Created by lixiuhai on 2015/9/17.
 */
public abstract class BaseDao implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
            initConfig();
    }

    public abstract void initConfig();
}
