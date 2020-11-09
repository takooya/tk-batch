package com.taikang.opt.core.batch.config;

import com.taikang.opt.core.Processor.CompanyBaseProcessor;
import com.taikang.opt.core.listener.CompanyBaseListener;
import com.taikang.opt.db.entity.CompBaseBySql;
import com.taikang.opt.db.entity.CompanyBase;
import com.taikang.opt.core.listener.StepListener;
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

/**
 * @author itw_chenhn
 * 基础表 companybase  跑批配置
 */
@Configuration
@Slf4j
public class CompBaseConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @PersistenceUnit
    private EntityManagerFactory em;

    @Autowired
    private CompanyBaseListener companyBaseListener;
    @Autowired
    private StepListener stepListener;


    /**
     * 建立step，步骤，包括reader，processor,writer
     */
    @Bean
    public Step compStep() {
        return stepBuilderFactory.get("compStep")
                .<CompBaseBySql, CompanyBase>chunk(100)
                .faultTolerant().retryLimit(3).retry(Exception.class)
                .reader(getCompReader())
                .processor(getCompProcessor())
                .writer(getCompWriter())
                .listener(stepListener)
                .build();
    }

    @Bean
    public ItemWriter<? super CompanyBase> getCompWriter() {
        JpaItemWriter<CompanyBase> jpaItemWriter = new JpaItemWriter<>();
        return (ItemWriter<CompanyBase>) list -> {
            jpaItemWriter.setEntityManagerFactory(em);
            jpaItemWriter.write(list);
        };
    }

    @Bean
    public ItemProcessor<? super CompBaseBySql, ? extends CompanyBase> getCompProcessor() {
        return new CompanyBaseProcessor();
    }

    /**
     * 建立一个job
     */
    @Bean
    public Job compBaseJob() {
        return jobBuilderFactory.get("compBaseJob")
                .incrementer(new RunIdIncrementer())
                .listener(companyBaseListener)
                .flow(compStep())
                .end()
                .build();
    }

    @Bean
    public ItemReader<CompBaseBySql> getCompReader() {
        log.info("CompBase--开始读数据");
        JpaPagingItemReader<CompBaseBySql> itemReader = new JpaPagingItemReader<>();
        String sql = "SELECT\n" +
                "\tmpcr.company_id ,\n" +
                "\torg.area_name,\n" +
                "\torg.area,\n" +
                "\tmb.belong_company,\n" +
                "\t org.org_name as belong_company_name,\n" +
                "\tmbi.plan_type,\n" +
                "\tmbi.is_focus_track,\n" +
                "  mc.industry,\n" +
                "  minctg.ctg_name as industry_name,\n" +
                "  mbi.fill_status as perfect_flag,\n" +
                "  mpcr.create_time as commit_date,\n" +
                " mbi.is_finish as finish_status\n"+
                "FROM\n" +
                "\t m_plan_comp_ref mpcr\n" +
                "inner JOIN m_company_info mc  ON  mpcr.company_id = mc.company_id AND mpcr.audit_status = 1 AND mpcr.mid = 65 \n" +
                "INNER JOIN m_busi_belong mb ON mb.company_id = mpcr.company_id AND mb.busi_type = 1801\n" +
                "LEFT JOIN area_org  org ON mb.belong_company = org.com_code \n" +
                "LEFT JOIN mannt_base_info mbi ON mbi.company_id = mc.company_id \n" +
                "LEFT JOIN m_indust_ctg minctg ON mc.industry = minctg.ctg_code ";
        try {
            JpaNativeQueryProvider<CompBaseBySql> queryProvider = new JpaNativeQueryProvider<>();
            queryProvider.setEntityClass(CompBaseBySql.class);
            queryProvider.setSqlQuery(sql);
            itemReader.setEntityManagerFactory(em);
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
