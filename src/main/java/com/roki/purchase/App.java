package com.roki.purchase;


import com.roki.purchase.config.AppConfig;
import org.springframework.boot.SpringApplication;


public class App {
    public static void main( String[] args )
    {
        SpringApplication.run(AppConfig.class,args);
    }
}
