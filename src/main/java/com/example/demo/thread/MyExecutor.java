package com.example.demo.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class MyExecutor {
    
	@Bean("taskExecutor")
    public static ThreadPoolTaskExecutor getExecutor() {
    	ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    	taskExecutor.setCorePoolSize(30);
    	taskExecutor.setMaxPoolSize(30);
    	taskExecutor.setQueueCapacity(50);
    	taskExecutor.setThreadNamePrefix("xuyalun");// 异步任务线程名以 xuyalun 为前缀
    	return taskExecutor;
    }
}