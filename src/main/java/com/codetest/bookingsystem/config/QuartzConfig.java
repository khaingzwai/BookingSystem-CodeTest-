package com.codetest.bookingsystem.config;

import com.codetest.bookingsystem.job.BookingExpirationJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail bookingExpirationJobDetail() {
        return JobBuilder.newJob(BookingExpirationJob.class)
                .withIdentity("bookingExpirationJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger bookingExpirationJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(bookingExpirationJobDetail())
                .withIdentity("bookingExpirationJobTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(1) // Check every minute
                        .repeatForever())
                .build();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(springBeanJobFactory());
        return schedulerFactoryBean;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new SpringBeanJobFactory();
    }
}