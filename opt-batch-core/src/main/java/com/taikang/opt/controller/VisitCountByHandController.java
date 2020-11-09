package com.taikang.opt.controller;

import com.taikang.nos.model.vo.ResponseData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author itw_chenhn
 * @date 2019-10-16
 */
@RestController
@RequestMapping(value = "VisitCount")
public class VisitCountByHandController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("visitCountJob")
    private Job job;
    @GetMapping
    public ResponseData hand(){
        JobParameters parameters = new JobParametersBuilder().addString("jobID", String.valueOf(System.currentTimeMillis())).toJobParameters();
        try {
            jobLauncher.run(job,parameters);
            return ResponseData.ok().data("VisitCount跑批成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error().data("VisitCount跑批失败");
        }
    }
}
