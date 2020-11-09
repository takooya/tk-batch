package com.taikang.opt.service;

import com.taikang.opt.Enum.AnnuHealthTypeEnum;
import com.taikang.opt.Enum.InitStatusEnum;
import com.taikang.opt.db.entity.CompanyBase;
import com.taikang.opt.db.entity.DriverMapDetail;
import com.taikang.opt.db.entity.Orgnization;
import com.taikang.opt.db.repository.DepartmentRepository;
import com.taikang.opt.db.repository.DriverMapDetailRepository;
import com.taikang.opt.util.OrgUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author itw_chenhn
 * 将companyBase封装成DriverMapDetail，并提供写入方法
 */
@SuppressWarnings("Duplicates")
@Service
public class DriverMapDetailService {
    private final DriverMapDetailRepository driverMapDetailRepository;
    private final DepartmentRepository departmentRepository;
    private final OrgUtil orgUtil;
    public Map<String, DriverMapDetail> driverMap = new HashMap<>();

    public DriverMapDetailService(DriverMapDetailRepository driverMapDetailRepository, DepartmentRepository departmentRepository, OrgUtil orgUtil) {
        this.driverMapDetailRepository = driverMapDetailRepository;
        this.departmentRepository = departmentRepository;
        this.orgUtil = orgUtil;
    }

    /**
     * 初始化map
     */
    public Map<String, DriverMapDetail> createMap() {
        List<Orgnization> orgnizations = departmentRepository.findAllByUpComCode("%");
        DriverMapDetail driverMapDetail = null;
        DriverMapDetail driverFocusMapDetail = null;
        for (Orgnization orgnization : orgnizations) {
            driverMapDetail = new DriverMapDetail();
            driverFocusMapDetail = new DriverMapDetail();
            driverMapDetail = createBelongInit(driverMapDetail, orgnization, "0");
            driverFocusMapDetail = createBelongInit(driverFocusMapDetail, orgnization, "1");
            driverMap.put(orgnization.getComCode(), driverMapDetail);
            driverMap.put(orgnization.getComCode() + "Focus", driverFocusMapDetail);
        }
        driverMap = createAreaInit(driverMap);
        return driverMap;
    }

    /**
     * 所有所属分公司记录初始化
     */
    private DriverMapDetail createBelongInit(DriverMapDetail driverMapDetail, Orgnization orgnization, String flag) {
        driverMapDetail.setOrg(orgnization.getComCode());
        driverMapDetail.setOrgName(orgUtil.convetCode2Name(orgnization.getComCode()));
        driverMapDetail.setBelongArea(orgnization.getArea());
        driverMapDetail.setBelongAreaName(orgnization.getAreaName());
        driverMapDetail.setConcernCtr(0.00);
        driverMapDetail.setConcernSelfR(0.00);
        driverMapDetail.setConcernAreaR(0.00);
        driverMapDetail.setWarnCtr(0.00);
        driverMapDetail.setWarnSelfR(0.00);
        driverMapDetail.setWarnAreaR(0.00);
        driverMapDetail.setHealthCtr(0.00);
        driverMapDetail.setHealthCtr(0.00);

        if ("0".equals(flag)) {
            driverMapDetail.setIsFocusTrack("0");
        } else {
            driverMapDetail.setIsFocusTrack("1");
        }
        return driverMapDetail;
    }

    /**
     * 所有大区记录数据初始化
     */
    private Map<String, DriverMapDetail> createAreaInit(Map<String, DriverMapDetail> driverMap) {

        /**
         *    初始化3个大区，6条数据
         * */
        DriverMapDetail southDriverMapDetail = new DriverMapDetail();
        southDriverMapDetail.setOrgName("南");
        southDriverMapDetail.setOrg("%01");
        DriverMapDetail southFocusDriverMapDetail = new DriverMapDetail();
        southFocusDriverMapDetail.setOrgName("南");
        southFocusDriverMapDetail.setOrg("%01");
        DriverMapDetail northDriverMapDetail = new DriverMapDetail();
        northDriverMapDetail.setOrgName("北");
        northDriverMapDetail.setOrg("%03");
        DriverMapDetail northFocusDriverMapDetail = new DriverMapDetail();
        northFocusDriverMapDetail.setOrgName("北");
        northFocusDriverMapDetail.setOrg("%03");
        DriverMapDetail midDriverMapDetail = new DriverMapDetail();
        midDriverMapDetail.setOrgName("中");
        midDriverMapDetail.setOrg("%02");
        DriverMapDetail midFocusDriverMapDetail = new DriverMapDetail();
        midFocusDriverMapDetail.setOrgName("中");
        midFocusDriverMapDetail.setOrg("%02");
        DriverMapDetail totalDriverMapDetail = new DriverMapDetail();
        totalDriverMapDetail.setOrg("%");
        totalDriverMapDetail.setOrgName("总公司");
        DriverMapDetail totalFocusDriverMapDetail = new DriverMapDetail();
        totalFocusDriverMapDetail.setOrg("%");
        totalFocusDriverMapDetail.setOrgName("总公司");
        driverMap.put("%01", southDriverMapDetail);
        driverMap.put("%01Focus", southFocusDriverMapDetail);
        driverMap.put("%02", midDriverMapDetail);
        driverMap.put("%02Focus", midFocusDriverMapDetail);
        driverMap.put("%03", northDriverMapDetail);
        driverMap.put("%03Focus", northFocusDriverMapDetail);
        driverMap.put("%", totalDriverMapDetail);
        driverMap.put("%Focus", totalFocusDriverMapDetail);
        return driverMap;
    }

    public void convert(CompanyBase companyBase) {
        if (driverMap == null || driverMap.size() == 0) {
            driverMap = createMap();
        }
        /**
         *    进行数据封装，调用私有方法
         *    每次进来一个企业， 封装3或者6条数据到一个map中，
         *    每次全部（全国，所属大区，所属分公司）为3条
         *    如果企业是关注的，则关注状态下再加3条
         * */
        driverMap = convertResult(driverMap, companyBase);
    }

    /**
     * 写入方法
     */
    public void writeMapDetail(List<DriverMapDetail> list) {
        driverMapDetailRepository.saveAll(list);
    }

    public List<DriverMapDetail> convert2DriverMapDetail(Map<String, DriverMapDetail> driverMap) {
        List<DriverMapDetail> details = new ArrayList<>();
        Collection<DriverMapDetail> values = driverMap.values();
        DriverMapDetail total = driverMap.get("%");
        DriverMapDetail totalFocus = driverMap.get("%Focus");
        DriverMapDetail southAreaTotal = driverMap.get("%01");
        DriverMapDetail southAreaFocus = driverMap.get("%01Focus");
        DriverMapDetail midAreaTotal = driverMap.get("%02");
        DriverMapDetail midAreaFocus = driverMap.get("%02Focus");
        DriverMapDetail northAreaTotal = driverMap.get("%03");
        DriverMapDetail northAreaFocus = driverMap.get("%03Focus");
        for (DriverMapDetail driverMapDetail : values) {
            /**
             *   全部
             * */
            if ("0".equals(driverMapDetail.getIsFocusTrack())) {
                if ("%".equals(driverMapDetail.getOrg())) {
                    /**
                     *   总公司不需要算比例，只有一个重点开拓占全国，后续在业务中计算即可
                     * */
                    continue;
                } else if (driverMapDetail.getOrg().startsWith("%")) {
                    driverAreaTooles(total, driverMapDetail);
                } else {
                    /**
                     *    各个分公司
                     * */
                    /**
                     *    该分公司是南区
                     * */
                    if ("%01".equals(driverMapDetail.getBelongArea())) {
                        driverBelongTools(total, southAreaTotal, driverMapDetail);
                    }
                    /**
                     *    中区
                     * */
                    else if ("%02".equals(driverMapDetail.getBelongArea())) {
                        driverBelongTools(total, midAreaTotal, driverMapDetail);
                    }
                    /**
                     *    北区
                     * */
                    else if ("%03".equals(driverMapDetail.getBelongArea())) {
                        driverBelongTools(total, northAreaTotal, driverMapDetail);
                    }
                }
            } else {
                /**
                 *    重点情况
                 * */
                if ("%".equals(driverMapDetail.getOrg())) {
                    /**
                     *   总公司不需要算比例，只有一个重点开拓占全国，后续在业务中计算即可
                     * */
                    continue;
                } else if (driverMapDetail.getOrg().startsWith("%")) {
                    driverAreaTooles(totalFocus, driverMapDetail);
                } else {
                    /**
                     *    各个分公司
                     * */
                    /**
                     *    该分公司是南区
                     * */
                    if ("%01".equals(driverMapDetail.getBelongArea())) {
                        driverBelongTools(totalFocus, southAreaFocus, driverMapDetail);
                    }
                    /**
                     *    中区
                     * */
                    else if ("%02".equals(driverMapDetail.getBelongArea())) {
                        driverBelongTools(totalFocus, midAreaFocus, driverMapDetail);
                    }
                    /**
                     *    北区
                     * */
                    else if ("%03".equals(driverMapDetail.getBelongArea())) {
                        driverBelongTools(totalFocus, northAreaFocus, driverMapDetail);
                    }
                }
            }
            details.add(driverMapDetail);
        }
        /**
         *   补充
         * */
        details.add(total);
        details.add(totalFocus);
        return details;
    }

    private void driverAreaTooles(DriverMapDetail total, DriverMapDetail source) {
        /**
         * 以%开头的均为大区
         * */

        if (total.getCompCt() > 0) {
            /**
             *   区公司总数占全国比例
             * */
            BigDecimal compCtr = new BigDecimal(source.getCompCt()).divide(new BigDecimal(total.getCompCt()), 4, BigDecimal.ROUND_HALF_DOWN);
            source.setCompCtr(compCtr.doubleValue());
            /**
             *   区警示状态企业数占全国比例
             * */
            BigDecimal warnCtr = new BigDecimal(source.getWarnCt()).divide(new BigDecimal(total.getCompCt()), 4, BigDecimal.ROUND_HALF_DOWN);
            source.setWarnCtr(warnCtr.doubleValue());
            /**
             *   区健康状态企业数占全国比例
             * */
            BigDecimal healthCtr = new BigDecimal(source.getHealthCt()).divide(new BigDecimal(total.getCompCt()), 4, BigDecimal.ROUND_HALF_DOWN);
            source.setHealthCtr(healthCtr.doubleValue());
            /**
             *    区关注状态企业数占全国比例
             * */
            BigDecimal concernCtr = new BigDecimal(source.getFocusCt()).divide(new BigDecimal(total.getCompCt()), 4, BigDecimal.ROUND_HALF_DOWN);
            source.setConcernCtr(concernCtr.doubleValue());
        } else {
            source.setCompCtr(0.00);
            source.setWarnCtr(0.00);
            source.setHealthCtr(0.00);
            source.setCompCtr(0.00);
        }





    }

    private void driverBelongTools(DriverMapDetail total, DriverMapDetail area, DriverMapDetail source) {
        if (source.getCompCt() != 0) {
            /**
             *    该分公司企业数占全国企业总数比例
             * */
            BigDecimal compCtr = new BigDecimal(source.getCompCt()).divide(new BigDecimal(total.getCompCt()), 4, BigDecimal.ROUND_HALF_DOWN);
            source.setCompCtr(compCtr.doubleValue());
            /**
             * 该分公司企业数占整个大区企业数比例
             * */
            BigDecimal areaR = new BigDecimal(source.getCompCt()).divide(new BigDecimal(area.getCompCt()), 4, BigDecimal.ROUND_HALF_DOWN);
            source.setAreaR(areaR.doubleValue());
            /**
             *  该分公司警示类企业数占本公司比例
             * */
            BigDecimal warnSelfR = new BigDecimal(source.getWarnCt()).divide(new BigDecimal(source.getCompCt()), 4, BigDecimal.ROUND_HALF_DOWN);
            source.setWarnSelfR(warnSelfR.doubleValue())
            /**
             *   该分公司警示类企业数占区中心总数比例
             * */;
            BigDecimal warnAreaR = new BigDecimal(source.getWarnCt()).divide(new BigDecimal(area.getCompCt()), 4, BigDecimal.ROUND_HALF_DOWN);
            source.setWarnAreaR(warnAreaR.doubleValue());
            /**
             *  该分公司警示类 企业数占全国企业总数比例
             * */
            BigDecimal warnCtr = new BigDecimal(source.getWarnCt()).divide(new BigDecimal(total.getCompCt()), 4, BigDecimal.ROUND_HALF_DOWN);
            source.setWarnCtr(warnCtr.doubleValue());
            /**
             *    该分公司关注类企业数占本公司比例
             * */
            BigDecimal concernSelfR = new BigDecimal(source.getFocusCt()).divide(new BigDecimal(source.getCompCt()), 4, BigDecimal.ROUND_HALF_DOWN);
            source.setConcernSelfR(concernSelfR.doubleValue());
            /**
             *   该分公司关注类企业数占区域比例
             * */
            BigDecimal concernAreaR = new BigDecimal(source.getFocusCt()).divide(new BigDecimal(area.getCompCt()), 4, BigDecimal.ROUND_HALF_DOWN);
            source.setConcernAreaR(concernAreaR.doubleValue());
            /**
             *   该分公司关注类企业数占全国比例
             * */
            BigDecimal concernCtr = new BigDecimal(source.getFocusCt()).divide(new BigDecimal(total.getCompCt()), 4, BigDecimal.ROUND_HALF_DOWN);
            source.setConcernCtr(concernCtr.doubleValue());
        }

    }


    public Map<String, DriverMapDetail> convertResult(Map<String, DriverMapDetail> driverMap, CompanyBase companyBase) {
        driverMap = convertUtil(driverMap, companyBase);
        return driverMap;
    }

    private Map<String, DriverMapDetail> convertUtil(Map<String, DriverMapDetail> driverMap, CompanyBase companyBase) {
        DriverMapDetail totalDriver;
        DriverMapDetail totalFocusDriver;
        DriverMapDetail areaDriver;
        DriverMapDetail areaFocusDriver;
        DriverMapDetail belongDriver;
        DriverMapDetail belongFocusDriver;
        /**
         *   isFocusTrack = false 代表是全部，true代表是重点
         * */
        boolean isFocusTrack = false;
        if ("1".equals(companyBase.getIsFocusTrack())) {
            isFocusTrack = true;
        }
        /**
         *   每次企业进来，全部的统计数量都要更改
         * */
        if (driverMap.keySet().contains("%")) {
            totalDriver = driverMap.get("%");
        } else {
            totalDriver = new DriverMapDetail();
        }
        if (driverMap.keySet().contains(companyBase.getArea())) {
            areaDriver = driverMap.get(companyBase.getArea());
        } else {
            areaDriver = new DriverMapDetail();
        }
        if (driverMap.keySet().contains(companyBase.getBelongCompany())) {
            belongDriver = driverMap.get(companyBase.getBelongCompany());
        } else {
            belongDriver = new DriverMapDetail();
        }
        if (isFocusTrack) {
            if (driverMap.keySet().contains("%Focus")) {
                totalFocusDriver = driverMap.get("%Focus");
            } else {
                totalFocusDriver = new DriverMapDetail();
            }
            if (driverMap.keySet().contains(companyBase.getArea() + "Focus")) {
                areaFocusDriver = driverMap.get(companyBase.getArea() + "Focus");
            } else {
                areaFocusDriver = new DriverMapDetail();
            }
            if (driverMap.keySet().contains(companyBase.getBelongCompany() + "Focus")) {
                belongFocusDriver = driverMap.get(companyBase.getBelongCompany() + "Focus");
            } else {
                belongFocusDriver = new DriverMapDetail();
            }
            totalFocusDriver = dealDriverMapDetail(totalFocusDriver, companyBase);
            areaFocusDriver = dealDriverMapDetail(areaFocusDriver, companyBase);
            belongFocusDriver = dealDriverMapDetail(belongFocusDriver, companyBase);
            /**
             *   总公司
             * */
            totalFocusDriver.setOrg("%");
            totalFocusDriver.setOrgName("总公司");
            totalFocusDriver.setBelongArea("");
            totalFocusDriver.setBelongAreaName("");
            totalFocusDriver.setIsFocusTrack("1");
            /**
             *   大区
             * */
            areaFocusDriver.setOrg(companyBase.getArea());
            areaFocusDriver.setOrgName(companyBase.getAreaName());
            areaFocusDriver.setBelongArea("");
            areaFocusDriver.setBelongAreaName("");
            areaFocusDriver.setIsFocusTrack("1");
            /**
             *   所属分公司
             * */
            belongFocusDriver.setIsFocusTrack("1");
            driverMap.put("%Focus", totalFocusDriver);
            driverMap.put(companyBase.getArea() + "Focus", areaFocusDriver);
            driverMap.put(companyBase.getBelongCompany() + "Focus", belongFocusDriver);
        }
        /**
         *   执行数量统计，无论企业是否重点，全部维度统计都需要进行更改
         * */
        totalDriver = dealDriverMapDetail(totalDriver, companyBase);
        areaDriver = dealDriverMapDetail(areaDriver, companyBase);
        belongDriver = dealDriverMapDetail(belongDriver, companyBase);
        /**
         *  封装其他数据，org，orgName，area，areaName
         * */
        /**
         *   总公司（全部）
         * */
        totalDriver.setOrg("%");
        totalDriver.setOrgName("总公司");
        totalDriver.setBelongArea("");
        totalDriver.setBelongAreaName("");
        totalDriver.setIsFocusTrack("0");
        /**
         *   大区（全部）
         * */
        areaDriver.setOrg(companyBase.getArea());
        areaDriver.setOrgName(companyBase.getAreaName());
        areaDriver.setBelongArea("");
        areaDriver.setBelongAreaName("");
        areaDriver.setIsFocusTrack("0");
        /**
         *   所属分公司（全部）
         * */
        belongDriver.setIsFocusTrack("0");
        driverMap.put("%", totalDriver);
        driverMap.put(companyBase.getArea(), areaDriver);
        driverMap.put(companyBase.getBelongCompany(), belongDriver);
        return driverMap;
    }


    /**
     * 数量+1
     **/
    private DriverMapDetail dealDriverMapDetail(DriverMapDetail driverMapDetail, CompanyBase companyBase) {
        //  公司总数
        driverMapDetail.setCompCt(driverMapDetail.getCompCt() + 1);
        //  单一计划企业数
        if ("01".equals(companyBase.getPlanType())) {
            driverMapDetail.setSpCt(driverMapDetail.getSpCt() + 1);
        } else if ("02".equals(companyBase.getPlanType())) {
            /**
             *   集合计划企业数
             * */
            driverMapDetail.setListPCt(driverMapDetail.getListPCt() + 1);
        }
        if (companyBase.getFinishStatus().equals(InitStatusEnum.FINISH.getCode())) {
            driverMapDetail.setFinishCt(driverMapDetail.getFinishCt() + 1);
        }
        // 重点开拓企业数量
        if ("1".equals(companyBase.getIsFocusTrack())) {
            driverMapDetail.setKeyCt(driverMapDetail.getKeyCt() + 1);
        }
        //健康各个状态的企业数量
        if (AnnuHealthTypeEnum.HEALTH.getCode().equals(companyBase.getHealthType())) {
            driverMapDetail.setHealthCt(driverMapDetail.getHealthCt() + 1);
        } else if (AnnuHealthTypeEnum.WARNING.getCode().equals(companyBase.getHealthType())) {
            driverMapDetail.setWarnCt(driverMapDetail.getWarnCt() + 1);
        } else if (AnnuHealthTypeEnum.FOCUS.getCode().equals(companyBase.getHealthType())) {
            driverMapDetail.setFocusCt(driverMapDetail.getFocusCt() + 1);
        }
        if ("1".equals(companyBase.getPerfectFlag())) {
            driverMapDetail.setPerfectCt(driverMapDetail.getPerfectCt() + 1);
        }
        driverMapDetail.setBelongArea(companyBase.getArea());
        driverMapDetail.setBelongAreaName(companyBase.getAreaName());
        driverMapDetail.setOrg(companyBase.getBelongCompany());
        driverMapDetail.setOrgName(companyBase.getBelongCompanyName());
        driverMapDetail.setCreateTime(new Date());
        // driverMapDetail.setIsFocusTrack(companyBase.getIsFocusTrack());
        return driverMapDetail;
    }
}
