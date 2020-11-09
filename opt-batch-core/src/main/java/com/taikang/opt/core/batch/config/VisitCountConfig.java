package com.taikang.opt.core.batch.config;

import com.taikang.opt.core.Processor.VisitCountProcessor;
import com.taikang.opt.core.listener.JobListener;
import com.taikang.opt.core.listener.VisitCountListener;
import com.taikang.opt.db.entity.CompBaseBySql;
import com.taikang.opt.db.entity.VisitCountBySql;
import com.taikang.opt.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.HashMap;

/**
 * @author itw_chenhn
 */
@Configuration
@Slf4j
public class VisitCountConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @PersistenceUnit
    private EntityManagerFactory em;
    @Autowired
    private VisitCountListener visitCountListener;
    @Autowired
    private TimeUtil timeUtil;

    @Bean
    public Job visitCountJob() {
        return jobBuilderFactory.get("visitCountJob")
                .incrementer(new RunIdIncrementer())
                .listener(visitCountListener)
                .flow(visitCountStep())
                .end()
                .build();
    }

    @Bean
    public Step visitCountStep() {
        return stepBuilderFactory.get("visitCountStep")
                .<VisitCountBySql, VisitCountBySql>chunk(100)
                .faultTolerant().retryLimit(1).retry(Exception.class)
                .reader(getVisitCountReader())
                .processor(getVisitCountProcessor())
                .writer(getVisitCountWriter())
                .build();
    }
    @Bean
    public ItemWriter<? super VisitCountBySql> getVisitCountWriter() {
        /**
         *    模拟写数据
         * */
        return list -> {
            for (VisitCountBySql visitCountBySql : list) {
                log.info(visitCountBySql.toString());
            }
        };
    }
    @Bean
    public ItemProcessor<? super VisitCountBySql, ? extends VisitCountBySql> getVisitCountProcessor() {
        return new VisitCountProcessor();
    }
    @Bean
    public ItemReader<? extends VisitCountBySql> getVisitCountReader() {
        JpaPagingItemReader<VisitCountBySql> itemReader = new JpaPagingItemReader<>();
        String sql = "SELECT\n" +
                "  mvl.id,\n" +
                "\tmvl.company_id,\n" +
                "\tmvl.visit_date,\n" +
                "  mvls.staff,\n" +
                "  mvls.post,\n" +
                "  mbb.belong_company as org \n" +
                "FROM\n" +
                "\tmannt_visit_log mvl \n" +
                "LEFT  JOIN mannt_visit_log_staff mvls ON mvl.id = mvls.visit_log_id\n" +
                "INNER  JOIN m_busi_belong mbb on mvl.company_id = mbb.company_id\n" +
                "AND mvl.audit_status = 1 and mvl.create_time BETWEEN :startTime AND :endTime";
        try {
            JpaNativeQueryProvider<VisitCountBySql> queryProvider = new JpaNativeQueryProvider<>();
            queryProvider.setEntityClass(VisitCountBySql.class);
            HashMap<String, Object> map = new HashMap<>();
            queryProvider.setSqlQuery(sql);
            String startTime = timeUtil.getTime(1);
            String endTime = timeUtil.getTime(0);
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            itemReader.setEntityManagerFactory(em);
            itemReader.setParameterValues(map);
            itemReader.setQueryProvider(queryProvider);
            itemReader.setPageSize(100);
            itemReader.afterPropertiesSet();
            itemReader.setSaveState(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemReader;
    }
}
