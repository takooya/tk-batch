package com.taikang.opt.core.batch.config;

import com.taikang.opt.core.Processor.DriverSortProcessor;
import com.taikang.opt.core.listener.DriverSortListener;
import com.taikang.opt.db.entity.DriverCompSort;
import com.taikang.opt.db.entity.DriverSortBySql;
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
import org.springframework.batch.item.database.JpaItemWriter;
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
 *
 */
@Configuration
@Slf4j
public class DriverCompSortConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @PersistenceUnit
    private EntityManagerFactory em;
    @Autowired
    private TimeUtil timeUtil;
    @Autowired
    private DriverSortListener driverSortListener;

    /**
     *    建立一个job
     * */
    @Bean
    public Job driverSort(){
        return jobBuilderFactory.get("driverSort")
                .incrementer(new RunIdIncrementer())
                .listener(driverSortListener)
                .flow(driverSortStep())
                .end()
                .build();
    }
    @Bean
    public Step driverSortStep() {
        return stepBuilderFactory.get("driverSortStep")
                .<DriverSortBySql,DriverCompSort>chunk(100)
                .faultTolerant().retryLimit(3).retry(Exception.class)
                .reader(getDriverSortReader())
                .processor(getDriverSortProcessor())
                .writer(getDriverSortWriter())
                .build();
    }
    @Bean
    public ItemWriter<? super DriverCompSort> getDriverSortWriter() {
       /* JpaItemWriter<DriverCompSort> itemWriter = new JpaItemWriter<>();
        return (ItemWriter<DriverCompSort>) list -> {
            itemWriter.setEntityManagerFactory(em);
            itemWriter.write(list);
        };*/
       /**
        *  改成模拟写数据，写数据的工作在整个job完成之后
        * */
       return list -> {
           for (DriverCompSort driverCompSort : list) {
               log.info(driverCompSort.toString());
           }
       };
    }

    @Bean
    public ItemProcessor<? super DriverSortBySql, ? extends DriverCompSort> getDriverSortProcessor() {
        return new DriverSortProcessor();
    }

    @Bean
    public ItemReader<? extends DriverSortBySql> getDriverSortReader() {
        JpaPagingItemReader<DriverSortBySql> itemReader = new JpaPagingItemReader<>();
        String sql = "SELECT count(1) as num ,belong_comp as comp\n" +
                " FROM visit_base vb WHERE visit_date BETWEEN :startTime AND :endTime  GROUP BY belong_comp ORDER BY num DESC";
       try {
           JpaNativeQueryProvider<DriverSortBySql> queryProvider = new JpaNativeQueryProvider<>();
           HashMap<String, Object> map = new HashMap<>();
           String[] beforeTime = timeUtil.getBeforeTime();
           map.put("startTime",beforeTime[0]);
           map.put("endTime",beforeTime[1]);
           queryProvider.setSqlQuery(sql);
           queryProvider.setEntityClass(DriverSortBySql.class);
           itemReader.setQueryProvider(queryProvider);
           itemReader.setEntityManagerFactory(em);
           itemReader.setParameterValues(map);
           itemReader.setPageSize(100);
           itemReader.afterPropertiesSet();
           itemReader.setSaveState(true);
       }catch (Exception e){
           e.printStackTrace();
       }
        return itemReader;
    }
}
