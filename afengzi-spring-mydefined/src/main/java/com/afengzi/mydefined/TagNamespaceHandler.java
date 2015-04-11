package com.afengzi.mydefined;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by klov on 2015/4/8.
 */
public class TagNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("client",new MyBeanDefinitionParser());
    }
}
