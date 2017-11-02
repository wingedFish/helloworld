package com.afengzi.lang;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixiuhai on 2017/9/25.
 */
public class FinalTest {

    private static final Map<String,Long> map = new HashMap<String, Long>();
    static {
        map.put("klov",22L);
    }
    @org.junit.Test
    public void test(){
        map.put("klov",55L);
        System.out.println(map);
    }
}
