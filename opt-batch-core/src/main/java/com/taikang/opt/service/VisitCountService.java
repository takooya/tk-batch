package com.taikang.opt.service;

import cn.hutool.core.collection.CollUtil;
import com.taikang.nos.model.utils.ToolUtil;
import com.taikang.opt.Enum.DicStaffPostEnum;
import com.taikang.opt.db.VO.VisitCountVO;
import com.taikang.opt.db.entity.Orgnization;
import com.taikang.opt.db.entity.VisitCount;
import com.taikang.opt.db.entity.VisitCountBySql;
import com.taikang.opt.db.repository.DepartmentRepository;
import com.taikang.opt.db.repository.VisitCountRepository;
import com.taikang.opt.util.OrgUtil;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author itw_chenhn
 */
@Service
public class VisitCountService {
    private final VisitCountRepository visitCountRepository;
    private final OrgUtil orgUtil;
    private final DepartmentRepository departmentRepository;
    /**
     * 机构编码
     * */
    private HashSet<String> orgSet = new HashSet<>(Arrays.asList("51","52","53","55","56","57","58","59","60","61","62"
    ,"63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","81","82","83","84","86"));

    public VisitCountService(VisitCountRepository visitCountRepository, OrgUtil orgUtil, DepartmentRepository departmentRepository) {
        this.visitCountRepository = visitCountRepository;
        this.orgUtil = orgUtil;
        this.departmentRepository = departmentRepository;
    }

    public Map<String, VisitCountVO> map = new HashMap<>();

    public Map<String, VisitCountVO> convert2Map(VisitCountBySql visitCountBySql) {
        if (map == null) {
            map = new HashMap<>();
        }
        /**
         *  对机构进行处理，如果长度在两位以上，代表是区域中心，需要截取两位
         * */
        String org = null;
        if (visitCountBySql.getOrg().length() > 2) {
            org = visitCountBySql.getOrg().substring(0, 2);
        } else {
            org = visitCountBySql.getOrg();
        }
        VisitCountVO visitCountVO = null;
        if (!map.keySet().contains(org)) {
            visitCountVO = new VisitCountVO();
        } else {
            visitCountVO = map.get(org);
        }
        dealVisitCountVO(visitCountVO,visitCountBySql,org);
        map.put(org,visitCountVO);
        return map;
    }
   /**
    *    工具类，封装VisitCountVO
    * */
    private void dealVisitCountVO(VisitCountVO visitCountVO, VisitCountBySql visitCountBySql,String org) {
        if (visitCountVO.getProjectNumSet() == null || visitCountVO.getProjectNumSet().size() == 0) {
            HashSet<String> set = new HashSet<>();
            set.add(visitCountBySql.getCompanyId());
            visitCountVO.setProjectNumSet(set);
        }else {
            visitCountVO.getProjectNumSet().add(visitCountBySql.getCompanyId());
            visitCountVO.setProjectNumSet(visitCountVO.getProjectNumSet());
        }
        if (DicStaffPostEnum.FIRSTCHARGE.getCode().equals(visitCountBySql.getPost())) {
            visitCountVO.setFirstChargeNum(visitCountVO.getFirstChargeNum() + 1);
        } else if (DicStaffPostEnum.ORGMEMBER.getCode().equals(visitCountBySql.getPost())) {
            visitCountVO.setOrgMemberNum(visitCountVO.getOrgMemberNum() + 1);
        } else if (DicStaffPostEnum.PARTCHARGE.getCode().equals(visitCountBySql.getPost())) {
            visitCountVO.setPartChargeNum(visitCountVO.getPartChargeNum() + 1);
        } else if (DicStaffPostEnum.SUPPORT.getCode().equals(visitCountBySql.getPost())) {
            visitCountVO.setSupportNum(visitCountVO.getSupportNum() + 1);
        } else {
            visitCountVO.setSalesmanNum(visitCountVO.getSalesmanNum() + 1);
        }
        visitCountVO.setProjectCount(visitCountVO.getProjectCount() + 1);
        visitCountVO.setOrg(orgUtil.convetCode2Name(org));
        visitCountVO.setOrgCategory(orgUtil.convert2Category(org));
        visitCountVO.setBelongCompCode(org);
        visitCountVO.setCreateTime(new Date());
    }

    /**
     * 保存接口
     */
    public void write(Map<String, VisitCountVO> map) {
        /**
         *  一共有36个所属分公司
         * */
        if(map.keySet().size() != 36){
            /**
             *    将余下的机构码添加到map中，（保证包括全部的分公司，没有拜访次数的数量赋值为0）
             * */
            orgSet.removeAll(map.keySet());
            for (String str : orgSet) {
                VisitCountVO visitCount = new VisitCountVO();
                String org = orgUtil.convetCode2Name(str);
                String category = orgUtil.convert2Category(str);
                visitCount.setOrg(org);
                visitCount.setBelongCompCode(str);
                visitCount.setOrgCategory(category);
                visitCount.setCreateTime(new Date());
                map.put(str, visitCount);
            }
        }
        /**
         *   查询数据库的目的是为了获取area
         * */
        List<Orgnization> orgnizations = departmentRepository.findAllByUpComCode("%");
        Map<String,String> areaMap = new HashMap<>();
        for (Orgnization orgnization : orgnizations) {
            areaMap.put(orgnization.getComCode(),orgnization.getArea());
        }
        Collection<VisitCountVO> values = map.values();
        List<VisitCount> visitCounts = new ArrayList<>();
        for (VisitCountVO value : values) {
            VisitCount visitCount = new VisitCount();
            if(value.getProjectNumSet() != null && value.getProjectNumSet().size() > 0){
                visitCount.setProjectNum(value.getProjectNumSet().size());
            }else {
                visitCount.setProjectNum(0);
            }
            visitCount.setArea(areaMap.get(value.getBelongCompCode()));
            ToolUtil.copyProperties(value,visitCount);
            visitCounts.add(visitCount);
        }
        visitCountRepository.saveAll(visitCounts);
    }

    public void  deleteAll(){
        visitCountRepository.deleteAll();
    }


}
