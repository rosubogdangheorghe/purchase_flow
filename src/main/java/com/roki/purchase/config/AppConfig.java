package com.roki.purchase.config;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan("com.roki.purchase.entity")
@ComponentScan("com.roki.purchase")
@EnableJpaRepositories("com.roki.purchase.repository")
@EnableScheduling
public class AppConfig {

}
