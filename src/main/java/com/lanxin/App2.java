package com.lanxin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan(basePackages = "com.lanxin.dao")
@ComponentScan(basePackages = "com.lanxin")
public class App2
{
    public static void main( String[] args )
    {
        SpringApplication.run(App2.class,args);
    }
}
