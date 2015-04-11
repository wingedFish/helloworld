package com.afengzi.util;

import java.lang.reflect.Method;

/**
 * Created by lixiuhai on 2015/3/28.
 *
 */
public class ReflectUtil {

    /**
     *
     * @return
     */
    public int calculateMethodCountInClass(Class clazz){
        Method[] methods = clazz.getDeclaredMethods();
        LoggerHelper.print(methods.length+"");
        return methods.length ;
    }

    public static void main(String[] args) {
        ReflectUtil reflectUtil = new ReflectUtil();
        reflectUtil.calculateMethodCountInClass(ReflectUtil.class) ;
    }

}
