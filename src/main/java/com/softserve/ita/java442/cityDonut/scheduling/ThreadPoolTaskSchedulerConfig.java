package com.softserve.ita.java442.cityDonut.scheduling;

import com.softserve.ita.java442.cityDonut.constant.ConstantValue;
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
        threadPoolTaskScheduler.setPoolSize(ConstantValue.EMAIL_SEND_TASK_POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("EmailSendTaskScheduler");
        return threadPoolTaskScheduler;
    }
}