package com.taikang.opt.core.Processor;

import com.taikang.nos.model.utils.ToolUtil;
import com.taikang.opt.db.entity.VisitBase;
import com.taikang.opt.db.entity.VisitBaseBySql;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author itw_chenhn
 *
 */
public class VisitBaseProcessor implements ItemProcessor<VisitBaseBySql,VisitBase> {
    @Override
    public VisitBase process(VisitBaseBySql visitBaseBySql) throws Exception {
        VisitBase visitBase = new VisitBase();
        ToolUtil.copyProperties(visitBaseBySql,visitBase);
        return visitBase;
    }
}
