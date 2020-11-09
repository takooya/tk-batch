package com.taikang.opt.core.batch.config;

import com.taikang.opt.core.Processor.VisitBaseProcessor;
import com.taikang.opt.db.entity.CompBaseBySql;
import com.taikang.opt.db.entity.CompanyBase;
import com.taikang.opt.db.entity.VisitBase;
import com.taikang.opt.db.entity.VisitBaseBySql;
import com.taikang.opt.util.TimeUtil;
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
 */
@SuppressWarnings("ALL")
@Configuration
public class VisitBaseConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @PersistenceUnit
    private EntityManagerFactory em;
    @Autowired
    private TimeUtil timeUtil;

    /**
     * 手动执行的跑批job
     */
    @Bean
    public Job visitBaseJob() {
        return jobBuilderFactory.get("visitBaseJob")
                .incrementer(new RunIdIncrementer())
                .flow(visitBaseStep())
                .end()
                .build();
    }

    @Bean
    public Step visitBaseStep() {
        return stepBuilderFactory.get("visitBaseStep")
                .<VisitBaseBySql, VisitBase>chunk(10)
                .faultTolerant().retryLimit(3).retry(Exception.class)
                .reader(getVisitBaseReader())
                .processor(getVisitBaseProcessor())
                .writer(getVisitBaseWriter())
                .build();
    }

    @Bean
    public ItemWriter<? super VisitBase> getVisitBaseWriter() {
        JpaItemWriter<VisitBase> jpaItemWriter = new JpaItemWriter<>();
        return (ItemWriter<VisitBase>) list -> {
            jpaItemWriter.setEntityManagerFactory(em);
            jpaItemWriter.write(list);
        };
    }

    @Bean
    public ItemProcessor<? super VisitBaseBySql, ? extends VisitBase> getVisitBaseProcessor() {
        return new VisitBaseProcessor();
    }

    @Bean
    public ItemReader<? extends VisitBaseBySql> getVisitBaseReader() {
        JpaPagingItemReader<VisitBaseBySql> itemReader = new JpaPagingItemReader<>();
        String sql = "SELECT\n" +
                "  mvl.id,\n" +
                "\tmvl.company_id,\n" +
                "\tmvl.visit_date,\n" +
                "  mvl.creator as creator_id,\n" +
                "  u.`name`as creator,\n" +
                "   mbb.belong_company as belong_comp,\n" +
                "  dd.`name` as belong_comp_name,\n" +
                "\tu.dept_name as create_dep,\n" +
                "  mci.company_name as comp_name,\n" +
                "  dd.area,\n" +
                "  dd.area_name ,\n" +
                "   mbi.is_focus_track," +
                " mbi.is_finish  as finish_flag\n" +
                "FROM\n" +
                "\tmannt_visit_log mvl\n" +
                "INNER JOIN `user` u\n" +
                "INNER JOIN m_company_info mci\n" +
                "INNER JOIN m_busi_belong mbb\n" +
                "INNER JOIN dic_orgnization dd \n" +
                "INNER JOIN mannt_base_info mbi\n" +
                "ON mvl.creator = u.login_id\n" +
                "AND mvl.company_id = mci.company_id\n" +
                "AND mbb.belong_company = dd.com_code\n" +
                "AND mbi.company_id = mvl.company_id  \n" +
                "AND mbb.company_id = mvl.company_id\n" +
                "AND mvl.audit_status = 1\n" +
                "AND mbb.busi_type = 1801" ;
        try {
            JpaNativeQueryProvider<VisitBaseBySql> queryProvider = new JpaNativeQueryProvider<>();
            queryProvider.setEntityClass(VisitBaseBySql.class);
            queryProvider.setSqlQuery(sql);
            itemReader.setQueryProvider(queryProvider);
            itemReader.setEntityManagerFactory(em);
            itemReader.setPageSize(3);
            itemReader.afterPropertiesSet();
            itemReader.setSaveState(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemReader;
    }

    /**
     * 按照时间自动执行的跑批任务
     */
    @Bean
    public Job visitBaseJobAuto() {
        return jobBuilderFactory.get("visitBaseJobAuto")
                .incrementer(new RunIdIncrementer())
                .flow(visitBaseStepAuto())
                .end()
                .build();
    }

    @Bean
    public Step visitBaseStepAuto() {
        return stepBuilderFactory.get("visitBaseStepAuto")
                .<VisitBaseBySql, VisitBase>chunk(100)
                .faultTolerant().retryLimit(3).retry(Exception.class)
                .reader(getVisitBaseReaderAuto())
                .processor(getVisitBaseProcessorAuto())
                .writer(getVisitBaseWriterAuto())
                .build();
    }

    @Bean
    public ItemWriter<? super VisitBase> getVisitBaseWriterAuto() {
        JpaItemWriter<VisitBase> jpaItemWriter = new JpaItemWriter<>();
        return (ItemWriter<VisitBase>) list -> {
            jpaItemWriter.setEntityManagerFactory(em);
            jpaItemWriter.write(list);
        };
    }

    @Bean
    public ItemProcessor<? super VisitBaseBySql, ? extends VisitBase> getVisitBaseProcessorAuto() {
        return new VisitBaseProcessor();
    }

    @Bean
    public ItemReader<? extends VisitBaseBySql> getVisitBaseReaderAuto() {
        JpaPagingItemReader<VisitBaseBySql> itemReader = new JpaPagingItemReader<>();
        String sql = "SELECT\n" +
                "  mvl.id,\n" +
                "\tmvl.company_id,\n" +
                "\tmvl.visit_date,\n" +
                "  mvl.creator as creator_id,\n" +
                "  u.`name`as creator,\n" +
                "   mbb.belong_company as belong_comp,\n" +
                "  dd.`name` as belong_comp_name,\n" +
                "\tu.dept_name as create_dep,\n" +
                "  mci.company_name as comp_name,\n" +
                "  dd.area,\n" +
                "  dd.area_name ,\n" +
                "   mbi.is_focus_track," +
                " mbi.is_finish  as finish_flag\n" +
                "FROM\n" +
                "\tmannt_visit_log mvl\n" +
                "INNER JOIN `user` u\n" +
                "INNER JOIN m_company_info mci\n" +
                "INNER JOIN m_busi_belong mbb\n" +
                "INNER JOIN dic_orgnization dd \n" +
                "INNER JOIN mannt_base_info mbi\n" +
                "ON mvl.creator = u.login_id\n" +
                "AND mvl.company_id = mci.company_id\n" +
                "AND mbb.belong_company = dd.com_code\n" +
                "AND mbi.company_id = mvl.company_id  \n" +
                "AND mbb.company_id = mvl.company_id\n" +
                "AND mvl.audit_status = 1\n" +
                "AND mbb.busi_type = '1801'\n" +
                "AND mvl.create_time BETWEEN :startTime AND :endTime";
        try {
            JpaNativeQueryProvider<VisitBaseBySql> queryProvider = new JpaNativeQueryProvider<>();
            HashMap<String, Object> map = new HashMap<>();
            String startTime = timeUtil.getTime(1);
            String endTime = timeUtil.getTime(0);
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            queryProvider.setEntityClass(VisitBaseBySql.class);
            queryProvider.setSqlQuery(sql);
            itemReader.setQueryProvider(queryProvider);
            itemReader.setEntityManagerFactory(em);
            itemReader.setParameterValues(map);
            itemReader.setPageSize(100);
            itemReader.afterPropertiesSet();
            itemReader.setSaveState(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemReader;
    }
}
