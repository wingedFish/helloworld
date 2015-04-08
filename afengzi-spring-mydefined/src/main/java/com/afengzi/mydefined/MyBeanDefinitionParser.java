package com.afengzi.mydefined;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 *
 * Created by klov on 2015/4/8.
 */
public class MyBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {

        String address = element.getAttribute("address");
        int port = Integer.parseInt(element.getAttribute("port"));
        String destination = element.getAttribute("destination");

        builder.addPropertyValue("address", address);
        builder.addPropertyValue("port", port);
        builder.addPropertyValue("destination", destination);
    }

    @Override
    protected Class getBeanClass(Element element) {
        return Client.class;//return domain class
    }
}
