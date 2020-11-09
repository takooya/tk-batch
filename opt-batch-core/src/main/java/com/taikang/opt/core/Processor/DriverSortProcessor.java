package com.taikang.opt.core.Processor;

import com.taikang.nos.model.utils.ToolUtil;
import com.taikang.opt.db.entity.DriverCompSort;
import com.taikang.opt.db.entity.DriverSortBySql;
import com.taikang.opt.service.DriverSortService;
import com.taikang.opt.util.OrgUtil;
import com.taikang.opt.util.TimeUtil;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author itw_chenhn
 */
public class DriverSortProcessor implements ItemProcessor<DriverSortBySql,DriverCompSort> {
    @Autowired
    private TimeUtil timeUtil;
    @Autowired
    private OrgUtil orgUtil;
    @Autowired
    private DriverSortService driverSortService;
    @Override
    public DriverCompSort process(DriverSortBySql driverSortBySql) throws Exception {
        DriverCompSort driverCompSort = new DriverCompSort();
        ToolUtil.copyProperties(driverSortBySql,driverCompSort);
        String[] time = timeUtil.getTime();
        String year = time[0];
        String month = String.valueOf(Integer.valueOf(time[1])-1);
        /**
         * 如果是09月份，需要将0删掉
         * */
        if(month.startsWith("0")){
            month = month.replace("0","");
        }
        driverCompSort.setYear(year);
        driverCompSort.setMonth(month);
        String compName = orgUtil.convetCode2Name(driverSortBySql.getComp());
        driverCompSort.setCompName(compName);
        /**
         *   把统计出来的排行榜对象放入map中，后续继续统计
         * */
        driverSortService.sortMap.put(driverCompSort.getComp(),driverCompSort);
        return driverCompSort;
    }

}
