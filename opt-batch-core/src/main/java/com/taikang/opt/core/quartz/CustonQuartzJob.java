package com.taikang.opt.core.quartz;

import lombok.Data;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author itw_chenhn
 *
 */
@Data
public class CustonQuartzJob extends QuartzJobBean {
    private String jobName;
    private JobLauncher jobLauncher;
    private JobRegistry jobRegistry;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        try {
            Job job = jobRegistry.getJob(jobName);
            JobParameters parameters = new JobParametersBuilder()
                    .addString("jobID", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();
            jobLauncher.run(job,parameters);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

