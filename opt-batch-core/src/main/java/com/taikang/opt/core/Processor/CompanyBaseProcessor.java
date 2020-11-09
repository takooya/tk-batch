package com.taikang.opt.core.Processor;

import com.taikang.nos.model.utils.ToolUtil;
import com.taikang.opt.Enum.AnnuHealthTypeEnum;
import com.taikang.opt.Enum.InitStatusEnum;
import com.taikang.opt.db.VO.VisitResult;
import com.taikang.opt.db.entity.CompBaseBySql;
import com.taikang.opt.db.entity.CompanyBase;
import com.taikang.opt.service.CompanyBaseService;
import com.taikang.opt.service.DriverIndustryDetailService;
import com.taikang.opt.service.DriverMapDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * @author itw_chenhn
 */
public class CompanyBaseProcessor implements ItemProcessor<CompBaseBySql, CompanyBase> {

    @Autowired
    private CompanyBaseService companyBaseService;
    @Autowired
    private DriverIndustryDetailService driverIndustryDetailService;

    @Autowired
    private DriverMapDetailService driverMapDetailService;
    private Map<String, VisitResult> map = null;
    private Map<String, Date> joinPlanTimeMap = null;


    @Override
    public CompanyBase process(CompBaseBySql compBaseBySql) throws Exception {
        if (map == null || map.size() == 0) {
            map = companyBaseService.getCompanyHealths();
        }
        if (joinPlanTimeMap == null || joinPlanTimeMap.size() == 0) {
            joinPlanTimeMap = companyBaseService.getJoinPlanMap();
        }
        CompanyBase companyBase = new CompanyBase();
        ToolUtil.copyProperties(compBaseBySql, companyBase);
        companyBaseInit(companyBase);
        /**
         *   查询企业的健康状态
         * */

        String[] healthType = companyBaseService.getHealthType(companyBase, map, joinPlanTimeMap);
        companyBase.setHealthType(healthType[0]);
        if(healthType[1] != null){
            companyBase.setJoinStatus(healthType[1]);
        }else {
            companyBase.setJoinStatus(InitStatusEnum.UNFINISH.getCode());
        }
        /**
         *   将CompanyBase转换成DriverMapDetail，存到全局变量map中
         * */
        driverMapDetailService.convert(companyBase);
        /**
         *   将companyBase转换成DriverIndustryDetail，存到全局变量map中
         * */
        driverIndustryDetailService.convert(companyBase);
        return companyBase;
    }
    /**
     *   工具类，对CompanyBase进行初始化
     * */
    private void companyBaseInit(CompanyBase companyBase) {
        if (StringUtils.isBlank(companyBase.getFinishStatus())) {
            companyBase.setFinishStatus(InitStatusEnum.UNFINISH.getCode());
        }
        if (StringUtils.isBlank(companyBase.getPerfectFlag())) {
            companyBase.setPerfectFlag(InitStatusEnum.UNFINISH.getCode());
        }
        if (StringUtils.isBlank(companyBase.getIsFocusTrack())) {
            companyBase.setIsFocusTrack(InitStatusEnum.UNFINISH.getCode());
        }
    }


}
