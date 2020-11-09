package com.taikang.opt.core.batch.config;

import com.taikang.opt.core.quartz.CustonQuartzJob;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * @author itw_chenhn
 */
@Configuration
public class QuartzConfig {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobRegistry jobRegistry;
    @Value("${time.companybase}")
    private String companybaseTime;
    @Value("${time.visitBaseJobAuto}")
    private String visitBaseJobAutoTime;
    @Value("${time.driverSort}")
    private String driverSortTime;
    @Value("${time.visitCount}")
    private String visitCountTime;
    @Value("${time.ManntFile}")
    private String manntFileTime;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    @Bean
    public JobDetail jobDetailByCompBase() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", "compBaseJob");
        jobDataMap.put("jobLauncher", jobLauncher);
        jobDataMap.put("jobRegistry", jobRegistry);
        return JobBuilder.newJob(CustonQuartzJob.class)
                .withIdentity("compBaseJob")
                .setJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    /**
     * 触发器
     */
    @Bean
    public CronTriggerFactoryBean compBaseTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailByCompBase());
        /**
         *   设置companyBase Job 的执行时间
         * */
        cronTriggerFactoryBean.setCronExpression(companybaseTime);
        return cronTriggerFactoryBean;
    }

    //---------------------------------------------------------------------------------
    @Bean
    public JobDetail jobDetailByVisitBaseAuto() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", "visitBaseJobAuto");
        jobDataMap.put("jobLauncher", jobLauncher);
        jobDataMap.put("jobRegistry", jobRegistry);
        return JobBuilder.newJob(CustonQuartzJob.class)
                .withIdentity("visitBaseJobAuto")
                .setJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    /**
     * 触发器
     */
    @Bean
    public CronTriggerFactoryBean visitBaseAutoTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailByVisitBaseAuto());
        /**
         *   设置companyBase Job 的执行时间
         * */
        cronTriggerFactoryBean.setCronExpression(visitBaseJobAutoTime);
        return cronTriggerFactoryBean;
    }

    // ------------------------------------------------------------------------
    @Bean
    public JobDetail jobDetailByDriverSort() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", "driverSort");
        jobDataMap.put("jobLauncher", jobLauncher);
        jobDataMap.put("jobRegistry", jobRegistry);
        return JobBuilder.newJob(CustonQuartzJob.class)
                .withIdentity("driverSort")
                .setJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    @Bean
    public CronTriggerFactoryBean driverSortTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailByDriverSort());
        /**
         *   设置 driverSort  的执行时间
         * */
        cronTriggerFactoryBean.setCronExpression(driverSortTime);
        return cronTriggerFactoryBean;
    }

    //------------------------------------------------------------------------------------
    @Bean
    public JobDetail jobDetailByVisitCount() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", "visitCountJob");
        jobDataMap.put("jobLauncher", jobLauncher);
        jobDataMap.put("jobRegistry", jobRegistry);
        return JobBuilder.newJob(CustonQuartzJob.class)
                .withIdentity("visitCountJob")
                .setJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    @Bean
    public CronTriggerFactoryBean visitCountTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailByVisitCount());
        /**
         *   设置 VisitCount  的执行时间
         * */
        cronTriggerFactoryBean.setCronExpression(visitCountTime);
        return cronTriggerFactoryBean;
    }

    // ------------------------------------------------------------------------------------
    @Bean
    public JobDetail jobDetailByManntFile() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", "updateManntFileJob");
        jobDataMap.put("jobLauncher", jobLauncher);
        jobDataMap.put("jobRegistry", jobRegistry);
        return JobBuilder.newJob(CustonQuartzJob.class)
                .withIdentity("updateManntFile")
                .setJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    @Bean
    public CronTriggerFactoryBean manntFileTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailByManntFile());
        /**
         *   设置 manntFileTime 的执行时间
         * */
        cronTriggerFactoryBean.setCronExpression(manntFileTime);
        return cronTriggerFactoryBean;
    }

    // ---------------------------------------------------------------------------------------
    @Bean
    @ConditionalOnBean(value = Trigger.class)
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(
                compBaseTrigger().getObject(),
                visitBaseAutoTrigger().getObject(),
                driverSortTrigger().getObject(),
                visitCountTrigger().getObject(),
                manntFileTrigger().getObject());
        schedulerFactoryBean.setJobDetails(
                jobDetailByCompBase(),
                jobDetailByVisitBaseAuto(),
                jobDetailByDriverSort(),
                jobDetailByVisitCount(),
                jobDetailByManntFile());
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        return schedulerFactoryBean;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

}
