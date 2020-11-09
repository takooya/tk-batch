package com.taikang.opt.core.listener;

import com.taikang.opt.service.VisitCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author itw_chenhn
 */
@Slf4j
@Component
public class VisitCountListener implements JobExecutionListener {
    private long startTime;
    @Autowired
    private VisitCountService visitCountService;

    @Override
    public void beforeJob(JobExecution jobExecution) {
     //    visitCountService.deleteAll();    增量不需要删除
        startTime = System.currentTimeMillis();
        log.info("job before" + jobExecution.getJobParameters());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("JOB STATUS is" + jobExecution.getStatus());
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("job finished");
            visitCountService.write(visitCountService.map);
            visitCountService.map = null;
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.info("job failed");
        }
        log.info("job cost time is " + (System.currentTimeMillis() - startTime));
    }
}
