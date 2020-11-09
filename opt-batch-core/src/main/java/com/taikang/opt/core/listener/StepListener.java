package com.taikang.opt.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author  itw_chenhn
 */
@Component
@Slf4j
public class StepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("step start");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("step end");
        return ExitStatus.COMPLETED;
    }
}
