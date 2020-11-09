package com.taikang.opt.service;

import com.taikang.opt.db.entity.CompanyBase;
import com.taikang.opt.db.entity.DriverIndustryDetail;
import com.taikang.opt.db.entity.IndustCtg;
import com.taikang.opt.db.repository.DriverIndustryDetailRepository;
import com.taikang.opt.db.repository.IndustryCtgRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author itw_chenhn
 */
@Service
public class DriverIndustryDetailService {
    private final DriverIndustryDetailRepository driverIndustryDetailRepository;
    private final IndustryCtgRepository industryCtgRepository;
    public Map<String, DriverIndustryDetail> map = new HashMap<>();
    public List<IndustCtg> industCtgs = new ArrayList<>();

    public DriverIndustryDetailService(DriverIndustryDetailRepository driverIndustryDetailRepository, IndustryCtgRepository industryCtgRepository) {
        this.driverIndustryDetailRepository = driverIndustryDetailRepository;
        this.industryCtgRepository = industryCtgRepository;
    }

    public Map<String, DriverIndustryDetail> queryIndustCtg() {
        /**
         * -1 代表是一级分类
         * */
        industCtgs = industryCtgRepository.findAllByParentCode("-1");
        for (IndustCtg industCtg : industCtgs) {
            DriverIndustryDetail detail = new DriverIndustryDetail();
            detail.setIndustry(industCtg.getCtgCode());
            detail.setIndustryName(industCtg.getCtgName());
            map.put(industCtg.getCtgCode(), detail);
        }
        return map;
    }

    public void convert(CompanyBase companyBase) {
        DriverIndustryDetail driverIndustryDetail = null;
        if (map == null || map.size() == 0) {
            map = queryIndustCtg();
        }
        if(companyBase.getIndustry() == null){
            companyBase.setIndustry("99");
            companyBase.setIndustryName("其他行业");
        }
        if (!map.keySet().contains(companyBase.getIndustry())) {
            driverIndustryDetail = new DriverIndustryDetail();
        } else {
            driverIndustryDetail = map.get(companyBase.getIndustry());
        }
        driverIndustryDetail = countDriverIndustryDetail(driverIndustryDetail, companyBase);
       // map.put(driverIndustryDetail.getIndustry(), driverIndustryDetail);
        if(companyBase.getIndustry() != null){
            map.put(companyBase.getIndustry(),driverIndustryDetail);
        }else {
            map.put(driverIndustryDetail.getIndustry(),driverIndustryDetail);
        }
    }

    /**
     * 转换成数据库实体类对象
     */
    private DriverIndustryDetail countDriverIndustryDetail(DriverIndustryDetail driverIndustryDetail, CompanyBase companyBase) {
        /**
         *   如果是1代表重点关注，重点关注的数量+1
         * */
        if ("1".equals(companyBase.getIsFocusTrack())) {
            driverIndustryDetail.setFocusCt(driverIndustryDetail.getFocusCt() + 1);
            driverIndustryDetail.setTotalCt(driverIndustryDetail.getTotalCt() + 1);
        } else {
            driverIndustryDetail.setTotalCt(driverIndustryDetail.getTotalCt() + 1);
        }
        driverIndustryDetail.setCreateTime(new Date());
        if (companyBase.getIndustry() != null) {
            driverIndustryDetail.setIndustryName(companyBase.getIndustryName());
            driverIndustryDetail.setIndustry(companyBase.getIndustry());
        } else {
            /**
             *   否则，都算到其他行业里,99代表其他行业机构码
             * */
            driverIndustryDetail.setIndustryName("其他行业");
            driverIndustryDetail.setIndustry("99");
        }
        return driverIndustryDetail;
    }

    public List<DriverIndustryDetail> convertResult(Map<String, DriverIndustryDetail> map) {
        List<DriverIndustryDetail> result = new ArrayList<>();
        Collection<DriverIndustryDetail> values = map.values();
        for (DriverIndustryDetail driverIndustryDetail : values) {
            result.add(driverIndustryDetail);
        }
        return result;
    }

    /**
     * 写入数据库方法
     */
    public void write(List<DriverIndustryDetail> list) {
        driverIndustryDetailRepository.saveAll(list);
    }
}
