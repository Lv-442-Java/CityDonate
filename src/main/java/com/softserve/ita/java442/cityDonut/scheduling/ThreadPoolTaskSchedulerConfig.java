package com.softserve.ita.java442.cityDonut.scheduling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ComponentScan(
        basePackages= "com.softserve.ita.java442.cityDonut.scheduling")
public class ThreadPoolTaskSchedulerConfig {

    public static int MESSAGE_DELAY_TIME = 5000;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        int threadPoolSize = 20;
        threadPoolTaskScheduler.setPoolSize(threadPoolSize);
        threadPoolTaskScheduler.setThreadNamePrefix("EmailSendTaskScheduler");
        return threadPoolTaskScheduler;
    }
}