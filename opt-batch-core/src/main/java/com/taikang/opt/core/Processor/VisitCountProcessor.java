package com.taikang.opt.core.Processor;

import com.taikang.opt.db.entity.VisitCountBySql;
import com.taikang.opt.service.VisitCountService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author itw_chenhn
 */
public class VisitCountProcessor implements ItemProcessor<VisitCountBySql,VisitCountBySql> {
    @Autowired
    private VisitCountService visitCountService;

    @Override
    public VisitCountBySql process(VisitCountBySql visitCountBySql) throws Exception {
        visitCountService.convert2Map(visitCountBySql);
        return visitCountBySql;
    }
}
