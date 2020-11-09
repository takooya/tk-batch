package com.taikang.opt;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.taikang.opt.db.repository")
@EntityScan(basePackages = "com.taikang.opt.db.entity")
@EnableBatchProcessing
@EnableScheduling
@PropertySource(value = {"classpath:timeConfig.properties"})
public class OptBatchApplcation {
    public static void main(String args[]) {
        SpringApplication.run(OptBatchApplcation.class, args);
    }
}
