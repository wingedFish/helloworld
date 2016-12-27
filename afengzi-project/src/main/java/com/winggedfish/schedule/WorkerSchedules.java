package com.winggedfish.schedule;

import java.lang.annotation.*;

/**
 * Created by lixiuhai on 2016/8/29.
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WorkerSchedules {
    WorkerScheduled[] value();
}
