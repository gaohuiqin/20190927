package com.lanxin.shiroconfig;

import com.lanxin.realm.MyCustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/9/26 0026.
 */
@Configuration
public class ShiroConfig {


    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;



    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager){

        RedisSessionDAO redisSessionDAO=new RedisSessionDAO(); //session缓存在redis
        redisSessionDAO.setRedisManager(redisManager);

        return redisSessionDAO;
    }
    @Bean
    public DefaultWebSessionManager defaultWebSessionManager(RedisSessionDAO redisSessionDAO){

        DefaultWebSessionManager defaultWebSessionManager=new DefaultWebSessionManager();

        defaultWebSessionManager.setSessionDAO(redisSessionDAO);

        return defaultWebSessionManager;
    }
    //配置redis相关信息
    @Bean
    public RedisManager redisManager(){

        RedisManager redisManager=new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setPassword(password);
        redisManager.setExpire(30*60);
        return redisManager;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisManager redisManager){

        RedisCacheManager redisCacheManager=new RedisCacheManager();

        redisCacheManager.setRedisManager(redisManager);

        return redisCacheManager;

    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){

        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();

        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);

        return defaultAdvisorAutoProxyCreator;
    }
    //开启shiro注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager){

        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor=new AuthorizationAttributeSourceAdvisor();

        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);

        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> map=new HashMap<String,String>();
        map.put("/login","anon");
        map.put("/logout","anon");
        map.put("/notlogin","anon");
        map.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        shiroFilterFactoryBean.setLoginUrl("/notlogin");//没有登录时跳转的url

        return shiroFilterFactoryBean;
    }
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){

        HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName("MD5");

        hashedCredentialsMatcher.setHashIterations(100);

        return hashedCredentialsMatcher;

    }
    @Bean
    public MyCustomRealm myCustomRealm(HashedCredentialsMatcher hashedCredentialsMatcher){

        MyCustomRealm myCustomRealm=new MyCustomRealm();

        myCustomRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        return myCustomRealm;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(MyCustomRealm myCustomRealm,RedisCacheManager redisCacheManager,DefaultWebSessionManager defaultWebSessionManager){

        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();

        defaultWebSecurityManager.setRealm(myCustomRealm);

        defaultWebSecurityManager.setCacheManager(redisCacheManager);

        defaultWebSecurityManager.setSessionManager(defaultWebSessionManager);

        return defaultWebSecurityManager;
    }
}
