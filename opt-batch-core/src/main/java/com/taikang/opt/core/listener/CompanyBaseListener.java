package com.taikang.opt.core.listener;

import com.taikang.opt.db.entity.DriverIndustryDetail;
import com.taikang.opt.db.entity.DriverMapDetail;
import com.taikang.opt.service.CompanyBaseService;
import com.taikang.opt.service.DriverIndustryDetailService;
import com.taikang.opt.service.DriverMapDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author itw_chenhn
 * company_base job监听器
 */
@Slf4j
@Component
public class CompanyBaseListener implements JobExecutionListener {
    private long startTime;
    @Autowired
    private DriverMapDetailService driverMapDetailService;
    @Autowired
    private DriverIndustryDetailService driverIndustryDetailService;
    @Autowired
    private CompanyBaseService companyBaseService;
    /**
     *   在每个job发生之前执行
     * */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        companyBaseService.deleteAllData();
        startTime = System.currentTimeMillis();
        log.info("job before"+jobExecution.getJobParameters());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("JOB STATUS is"+ jobExecution.getStatus());
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            log.info("job finished");
            /**
             *    存到 DriverMapDetail表中
             * */
            List<DriverMapDetail> details = driverMapDetailService.convert2DriverMapDetail(driverMapDetailService.driverMap);
            if(details != null&&details.size() > 0){
                driverMapDetailService.writeMapDetail(details);
            }
          driverMapDetailService.driverMap = new HashMap<>();
            /**
             *  存到DriverMapIndustryDetail
             * */
            List<DriverIndustryDetail> detailList = driverIndustryDetailService.convertResult(driverIndustryDetailService.map);
            if(detailList != null && detailList.size() > 0){
                driverIndustryDetailService.write(detailList);
            }
            driverIndustryDetailService.map = new HashMap<>();
        }else if(jobExecution.getStatus() ==BatchStatus.FAILED){
            log.info("job failed");
        }
        log.info("job cost time is "+(System.currentTimeMillis() -startTime));
    }
}
