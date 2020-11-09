package com.taikang.opt.controller;

import com.taikang.nos.model.vo.ResponseData;
import com.taikang.opt.service.CompanyBaseService;
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
 * @date 2019-10-15
 */
@RestController
@RequestMapping(value = "companyBase")
public class CompanyBaseByHandController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("compBaseJob")
    private Job job;
    @Autowired
    private CompanyBaseService companyBaseService;

    @GetMapping
    public ResponseData hand(){
        /**
         *   companyBase 基础表是全量数据，每次跑批都要清空数据
         * */
        companyBaseService.deleteAllData();
        JobParameters parameters = new JobParametersBuilder().addString("jobID", String.valueOf(System.currentTimeMillis())).toJobParameters();
        try {
            jobLauncher.run(job,parameters);
            return ResponseData.ok().data("企业基础表跑批成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ok().data("企业基础表跑批失败，原因"+ e.getMessage());
        }
    }
}