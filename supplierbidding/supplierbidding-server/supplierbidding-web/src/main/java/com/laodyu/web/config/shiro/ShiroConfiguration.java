package com.laodyu.web.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/28
 * @Version 1.0
 **/
@Configuration
public class ShiroConfiguration {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //必须设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //自定义拦截器
        Map<String, Filter> filterMap = new LinkedHashMap<>();

        //自定义的权限
        filterMap.put("requestURL",getURLPathMatchFilter());
        filterMap.put("authc",new ShiroFormAuthenticationFilter());


        /***
         * 配置映射关系
         * authc: 所有url都必须认证通过才可以访问;
         * anno:所有url都可以匿名访问
         */
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/register","anon");
        filterChainDefinitionMap.put("/tending/**","authc");
        filterChainDefinitionMap.put("/bid/**","authc");
        filterChainDefinitionMap.put("/project/**","authc");
        filterChainDefinitionMap.put("/notice/**","authc");
        filterChainDefinitionMap.put("/system/**","authc");
        filterChainDefinitionMap.put("/expert/**","authc");
        filterChainDefinitionMap.put("/workflow/**","authc");
        filterChainDefinitionMap.put("/supplier/**","authc");
        //下面的配置路径 都需要在上面配置 authc 否则访问不到filter
        filterChainDefinitionMap.put("/tending/**","requestURL");
        filterChainDefinitionMap.put("/bid/**","requestURL");
        filterChainDefinitionMap.put("/project/**","requestURL");
        filterChainDefinitionMap.put("/notice/**","requestURL");
        filterChainDefinitionMap.put("/system/**","requestURL");
        filterChainDefinitionMap.put("/expert/**","requestURL");
        filterChainDefinitionMap.put("/workflow/**","requestURL");
        filterChainDefinitionMap.put("/supplier/**","requestURL");
        filterChainDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setFilters(filterMap);
        return shiroFilterFactoryBean;
    }



    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setRealm(getMyRealm());
        return securityManager;
    }

    @Bean
    public MyRealm getMyRealm() {
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    public URLPathMatchFilter getURLPathMatchFilter() {
        return new URLPathMatchFilter();
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(259200);
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie);
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }


}
