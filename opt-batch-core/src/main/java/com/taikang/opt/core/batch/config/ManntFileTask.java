package com.taikang.opt.core.batch.config;

import com.taikang.opt.service.ManntFileService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author itw_chenhn
 */
@Component
public class ManntFileTask implements Tasklet {
    @Autowired
    private ManntFileService manntFileService;
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        manntFileService.updateManntFile();
        return RepeatStatus.FINISHED;
    }
}
