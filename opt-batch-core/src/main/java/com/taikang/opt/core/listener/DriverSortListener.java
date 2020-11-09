package com.taikang.opt.core.listener;

import com.taikang.opt.service.DriverSortService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author itw_chenhn
 * @date 2019-10-28
 */
@Component
@Slf4j
public class DriverSortListener implements JobExecutionListener {
    @Autowired
    private DriverSortService driverSortService;
    private long startTime;
    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = System.currentTimeMillis();
        log.info("job before"+jobExecution.getJobParameters());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("JOB STATUS is"+ jobExecution.getStatus());
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            log.info("job finished");
            driverSortService.hand(driverSortService.sortMap);
        }else if(jobExecution.getStatus() ==BatchStatus.FAILED){
            log.info("job failed");
        }
        log.info("job cost time is "+(System.currentTimeMillis() -startTime));


    }
}
