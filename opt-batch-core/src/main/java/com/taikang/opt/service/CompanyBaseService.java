package com.taikang.opt.service;

import com.taikang.opt.Enum.AnnuHealthTypeEnum;
import com.taikang.opt.Enum.InitStatusEnum;
import com.taikang.opt.Enum.PlanEnum;
import com.taikang.opt.Enum.TimeStatusEnum;
import com.taikang.opt.db.VO.VisitResult;
import com.taikang.opt.db.entity.CompanyBase;
import com.taikang.opt.db.entity.PlanCompRef;
import com.taikang.opt.db.repository.*;
import com.taikang.opt.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author itw_chenhn
 * 封装companyBase跑批的相关操作
 */
@Component
public class CompanyBaseService {
    private final PlanCompRefRepository planCompRefRepository;
    private final VisitLogRepository visitLogRepository;
    private final TimeUtil timeUtil;
    private final CompBaseRepository compBaseRepository;
    private final DriverMapDetailRepository driverMapDetailRepository;
    private final DriverIndustryDetailRepository driverIndustryDetailRepository;

    public CompanyBaseService(PlanCompRefRepository planCompRefRepository, VisitLogRepository visitLogRepository, TimeUtil timeUtil, CompBaseRepository compBaseRepository, DriverMapDetailRepository driverMapDetailRepository, DriverIndustryDetailRepository driverIndustryDetailRepository) {
        this.planCompRefRepository = planCompRefRepository;
        this.visitLogRepository = visitLogRepository;
        this.timeUtil = timeUtil;
        this.compBaseRepository = compBaseRepository;
        this.driverMapDetailRepository = driverMapDetailRepository;
        this.driverIndustryDetailRepository = driverIndustryDetailRepository;
    }

    public Map<String, Date> getJoinPlanMap() {
        HashMap<String, Date> joinPlanMap = new HashMap<>();
        List<PlanCompRef> planCompRefs = planCompRefRepository.findAllByMidAndAuditStatus(PlanEnum.ANNUITYPLAN.getValue(), "1");
        for (PlanCompRef planCompRef : planCompRefs) {
            joinPlanMap.put(planCompRef.getCompanyId(), planCompRef.getCreateTime());
        }
        return joinPlanMap;
    }

    private List<String> getCompanyIds() {
        /**
         *   mid 后续更改枚举类
         * */
        List<PlanCompRef> planCompRefs = planCompRefRepository.findAllByMidAndAuditStatus(PlanEnum.ANNUITYPLAN.getValue(), "1");
        ArrayList<String> companyList = planCompRefs.stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.getCompanyId());
        }, ArrayList::addAll);
        return companyList;
    }

    /**
     * 生成map,key—companyid  value 包括 这个companyid 的拜访结果
     */
    public Map<String, VisitResult> getCompanyHealths() {
        HashMap<String, VisitResult> resultHashMap = new HashMap<>(getCompanyIds().size());
        /**
         *   年金作战地图全部有效企业ids(status = 1)
         * */
        List<String> companyIds = getCompanyIds();
        /**
         *    获取三个时间 当前时间，14天，30天前的时间
         * */
        String endTime = timeUtil.getTime(0);
        String twoWeekTime = timeUtil.getTime(14);
        String oneMonthTime = timeUtil.getTime(30);
        /**
         *    查询两周内拜访的企业和拜访的次数(从当前时间查起)
         * */
        List<Map> twoWeekVisits = visitLogRepository.compVisitQuery(twoWeekTime, endTime);
        /**
         *    查询30天内拜访的企业和拜访的次数(从当前时间查起)
         * */
        List<Map> oneMonthVisits = visitLogRepository.compVisitQuery(oneMonthTime, twoWeekTime);
        /**
         *   统计14-30天内的访问次数
         * */
        for (Map oneMonthVisit : oneMonthVisits) {
            VisitResult visitResult = new VisitResult();
            visitResult.setCompanyId((String) oneMonthVisit.get("company_id"));
            visitResult.setOneMonthVisitNum(Integer.valueOf(oneMonthVisit.get("visitNum") + ""));
            resultHashMap.put(visitResult.getCompanyId(), visitResult);
        }
        /**
         *   统计两周内的访问次数
         * */
        for (Map twoWeekVisit : twoWeekVisits) {
            VisitResult visitResult = new VisitResult();
            visitResult.setCompanyId((String) twoWeekVisit.get("company_id"));
            visitResult.setTwoWeekVisitNum(Integer.valueOf(twoWeekVisit.get("visitNum") + ""));
            resultHashMap.put(visitResult.getCompanyId(), visitResult);
        }
        /*for (Map twoWeekVisit : twoWeekVisits) {
            *//**
             *   如果map中包括companyid
             * *//*
            VisitResult visitResult = null;
            if (resultHashMap.keySet().contains(String.valueOf(twoWeekVisit.get("company_id")))) {
                visitResult = resultHashMap.get(String.valueOf(twoWeekVisit.get("company_id")));
                visitResult.setTwoWeekVisitNum(Integer.valueOf(twoWeekVisit.get("visitNum") + ""));
            } else {
                visitResult = new VisitResult();
                visitResult.setCompanyId(String.valueOf(twoWeekVisit.get("company_id")));
                visitResult.setTwoWeekVisitNum(Integer.valueOf(twoWeekVisit.get("visitNum") + ""));
            }
            resultHashMap.put(String.valueOf(twoWeekVisit.get("company_id")), visitResult);
        }*/
        /**
         *   将其他未拜访的企业id放入map中
         * */
        HashSet<String> companyIdSet = new HashSet<>(companyIds);
        /**
         *    去掉map中的companyid集合，剩下的为未拜访的企业
         * */
        companyIdSet.removeAll(resultHashMap.keySet());
        for (String companyId : companyIdSet) {
            resultHashMap.put(companyId, null);
        }
        return resultHashMap;
    }

    /**
     *
     * */
    public Date transDate(Date date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(date);
        Date dateResult = dateFormat.parse(format);
        return dateResult;
    }

    /**
     * 查询企业的健康状态
     */
    public String[] getHealthType(CompanyBase companyBase, Map<String, VisitResult> map, Map<String, Date> joinPlanMap) throws ParseException {
        String[] type = new String[2];
        /**
         *  这个企业拜访次数
         * */
        VisitResult visitResult = map.get(companyBase.getCompanyId());
        /**
         *  这个企业加入地图的时间
         * */
        Date joinDate = transDate(joinPlanMap.get(companyBase.getCompanyId()));
        boolean b = joinForTime(joinDate, transDate(timeUtil.getDate(TimeStatusEnum.WAITTIME.getCode())));
        /**
         *   如果企业已经结项，状态就为健康
         * */
        if (companyBase.getFinishStatus().equals(InitStatusEnum.FINISH.getCode())) {
            type[0] = AnnuHealthTypeEnum.FOCUS.getCode();
            return type;
        }
        /**
         *   该企业既有拜访次数，并且短期内没有加入作战地图
         * */
        if (visitResult != null && !b) {
            /**
             *   如果企业两周内有拜访次数，则为健康状态
             * */
            if (visitResult.getTwoWeekVisitNum() != null && visitResult.getTwoWeekVisitNum() >= 1) {
                type[0] = AnnuHealthTypeEnum.HEALTH.getCode();
            } else if (visitResult.getOneMonthVisitNum() != null && visitResult.getOneMonthVisitNum() >= 1) {
                /**
                 *   如果企业两周内没有拜访记录，但一月内有拜访记录，则为关注状态
                 * */
                type[0] = AnnuHealthTypeEnum.FOCUS.getCode();
            } else {
                /**
                 *   否则为警告
                 * */
                type[0] = AnnuHealthTypeEnum.WARNING.getCode();
            }
        } else {
            if (b) {
                type[0] = AnnuHealthTypeEnum.HEALTH.getCode();
                // type[1] 存储是否刚加入作战地图的标志
                type[1] = InitStatusEnum.FINISH.getCode();
            } else {
                type[0] = AnnuHealthTypeEnum.WARNING.getCode();
            }
        }
        return type;
    }

    /**
     * 通过企业加入作战地图的时间来判断是否健康
     */
    private boolean joinForTime(Date joinDate, Date nowDate) {
        return joinDate.getTime() >= nowDate.getTime();
    }

    /**
     * 清除数据接口
     */
    public void deleteAllData() {
        compBaseRepository.deleteAll();
        driverIndustryDetailRepository.deleteAll();
        driverMapDetailRepository.deleteAll();
    }


}
