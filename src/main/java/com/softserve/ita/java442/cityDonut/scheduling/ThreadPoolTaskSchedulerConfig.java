package com.softserve.ita.java442.cityDonut.scheduling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ComponentScan(
        basePackages= "com.softserve.ita.java442.cityDonut.scheduling")
public class ThreadPoolTaskSchedulerConfig {

    public static int threadPoolSize = 5;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(threadPoolSize);
        threadPoolTaskScheduler.setThreadNamePrefix(
                "MyTaskScheduler");
        return threadPoolTaskScheduler;
    }

    /*threadPoolTaskScheduler.schedule(
                new SendEmailNotificationTask("my message"),
                new Date(System.currentTimeMillis() + 5000)
     );*/
}