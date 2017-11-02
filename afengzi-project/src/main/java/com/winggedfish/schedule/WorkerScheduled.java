package com.winggedfish.schedule;


import java.lang.annotation.*;

/**
 * Created by lixiuhai on 2016/8/26.
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(value = WorkerSchedules.class)
public @interface WorkerScheduled {

    String scheduleId() default "";

    String cron() default "";

    boolean autoStartup() default false;

    int startupDelay() default 0 ;

    int threadCount() default 1 ;

    boolean concurrent() default false ;
}
