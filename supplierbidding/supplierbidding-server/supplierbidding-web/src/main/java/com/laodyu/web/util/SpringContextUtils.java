package com.laodyu.web.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/30
 * @Version 1.0
 **/
@Component
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextUtils.context = context;
    }

    public static ApplicationContext getContext(){
        return context;
    }
}
