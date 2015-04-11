package com.afengzi.util.domain;

import com.afengzi.util.ReflectUtil;
import org.junit.Test;

/**
 * Created by lixiuhai on 2015/3/28.
 */
public class ReflectUtilTest {
    @Test
    public void testReflectUtil(){
        ReflectUtil reflectUtil = new ReflectUtil();
        reflectUtil.calculateMethodCountInClass(RechargeOrder.class) ;
    }
}
