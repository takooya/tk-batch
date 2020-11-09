package com.taikang.opt.core.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author itw_chenhn
 * manntFile 文件跑批，每个月跑一次，把前一个月的数据进行处理
 * 处理包括： fileBase64 字段置为null
 * operatorStatus 改为文件已过期
 * fileStatus 改为3 -------自定义状态码
 */
@Configuration
@Slf4j
public class manntFileConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ManntFileTask manntFileTask;

    @Bean
    public Job updateManntFileJob(){
        return jobBuilderFactory.get("updateManntFileJob")
                .incrementer(new RunIdIncrementer())
                .flow(updateManntFileStep())
                .end()
                .build();
    }

    @Bean
    public Step updateManntFileStep(){
        return stepBuilderFactory.get("updateManntFileStep")
                .tasklet(manntFileTask)
                .build();
    }
}

