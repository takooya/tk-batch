package com.taikang.opt.controller;

import com.taikang.nos.model.vo.ResponseData;
import com.taikang.opt.db.repository.DriverCompSortRepository;
import com.taikang.opt.service.DriverSortService;
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
@RequestMapping(value = "DriverSort")
public class DriverSortByHandController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("driverSort")
    private Job job;
    @Autowired
    private DriverSortService driverSortService;

    @GetMapping
    public ResponseData hand() {
        /**
         *  先删除，后增补，全量跑批
         * */
        driverSortService.delteAllData();
        JobParameters parameters = new JobParametersBuilder().addString("jobID", String.valueOf(System.currentTimeMillis())).toJobParameters();
        try {
            jobLauncher.run(job, parameters);
            return ResponseData.ok().data("driverSort跑批成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error().data("driverSort跑批失败");
        }
    }
}
