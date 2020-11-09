package com.taikang.opt.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author itw_chenhn
 *   job 监听器
 */
@Component
@Slf4j
public class JobListener implements JobExecutionListener {
    private long startTime;
    /**
     *   在每个job发生之前执行
     * */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = System.currentTimeMillis();
        log.info("job before"+jobExecution.getJobParameters());
    }
     /**
      *   在每个job执行之后执行（本次使用监听器记录一个job执行的时间，并使用日志记录）
      * */
    @Override
    public void afterJob(JobExecution jobExecution) {
       log.info("JOB STATUS is"+ jobExecution.getStatus());
       if(jobExecution.getStatus() == BatchStatus.COMPLETED){
           log.info("job finished");
       }else if(jobExecution.getStatus() ==BatchStatus.FAILED){
           log.info("job failed");
       }
       log.info("job cost time is "+(System.currentTimeMillis() -startTime));
    }
}
