package com.fods.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncThreadPoolConfig {
    @Bean(name = "asyncThreadPoolExecutor")
    public Executor asyncThreadPoolExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setThreadNamePrefix("home-page-service-thread-");
        taskExecutor.setQueueCapacity(150);
        taskExecutor.initialize();
        return taskExecutor;
    }
}
