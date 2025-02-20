package com.kantboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

// 开启定时任务，用于@Scheduled等注解
// Enable scheduling, used for @Scheduled and other annotations
@EnableScheduling
// 开启异步，用于@Async等注解
// Enable asynchronous, used for @Async and other annotations
@EnableAsync(proxyTargetClass = true)
// 开启缓存，用于@Cacheable等注解
// Enable caching, used for @Cacheable and other annotations
@EnableCaching
// 开启JPA审计，用于@CreatedDate等注解
// Enable JPA auditing, used for @CreatedDate and other annotations
@EnableJpaAuditing
// 开启JPA扫描，用于@Entity等注解
// Enable JPA scanning, used for @Entity and other annotations
@EntityScan(basePackages = {"com.kantboot"})
// 开启JPA仓库扫描，用于@EnableJpaRepositories等注解
// Enable JPA repository scanning, used for @EnableJpaRepositories and other annotations
@EnableJpaRepositories(basePackages = {"com.kantboot"})
// 开启servlet组件扫描，用于@WebServlet等注解
// Enable servlet component scanning, used for @WebServlet and other annotations
@ServletComponentScan
@SpringBootApplication(scanBasePackages = {"com.kantboot"})
public class KantbootStartApplication {
    public static void main(String[] args) {
//        System.setProperty("https.proxyHost", "127.0.0.1");
//        System.setProperty("https.proxyPort", "7890");
        SpringApplication.run(KantbootStartApplication.class, args);

    }

}
