package com.winggedfish.schedule;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.util.StringValueResolver;

/**
 * Created by lixiuhai on 2016/8/26.
 */
public class WorkerScheduledAnnotationBeanPostProcessor implements BeanPostProcessor, Ordered, EmbeddedValueResolverAware, ApplicationContextAware,
        ApplicationListener<ContextRefreshedEvent>, DisposableBean {
    private StringValueResolver embeddedValueResolver;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext =applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
       this.embeddedValueResolver = resolver ;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
