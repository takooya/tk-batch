package com.taikang.opt.service;

import com.taikang.opt.db.VO.SortVO;
import com.taikang.opt.db.entity.DriverCompSort;
import com.taikang.opt.db.repository.DriverCompSortRepository;
import com.taikang.opt.util.OrgUtil;
import com.taikang.opt.util.TimeUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.*;

/**
 * @author itw_chenhn
 * @date 2019-10-16
 */
@Service
public class DriverSortService {
    private final DriverCompSortRepository driverCompSortRepository;
    private final OrgUtil orgUtil;
    private final TimeUtil timeUtil;

    public DriverSortService(DriverCompSortRepository driverCompSortRepository, OrgUtil orgUtil, TimeUtil timeUtil) {
        this.driverCompSortRepository = driverCompSortRepository;
        this.orgUtil = orgUtil;
        this.timeUtil = timeUtil;
    }

    /**
     * sortMap 用于封装全部的数据库对象，key为分公司comcode
     */
    public Map<String, DriverCompSort> sortMap = new HashMap<>();
    /**
     * sortTempMap 用于封装排行榜中间数据
     */
    private LinkedHashMap<String, SortVO> sortTempMap = new LinkedHashMap<>();

    /**
     * 机构编码
     */
    private HashSet<String> orgSet = new HashSet<>(Arrays.asList("51", "52", "53", "55", "56", "57", "58", "59", "60", "61", "62"
            , "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "81", "82", "83", "84", "86"));

    public void delteAllData() {
        driverCompSortRepository.deleteAll();
    }

    /**
     * 排序，按照num从大到小
     */
    private Map<String, DriverCompSort> sortFirstMap(Map<String, DriverCompSort> map) {
        List<Map.Entry<String, DriverCompSort>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, DriverCompSort>>() {
            @Override
            public int compare(Map.Entry<String, DriverCompSort> o1, Map.Entry<String, DriverCompSort> o2) {
                return o2.getValue().getNum() - o1.getValue().getNum();
            }
        });
        Map<String, DriverCompSort> resultMap = new LinkedHashMap<>();
        for (Map.Entry<String, DriverCompSort> entry : list) {
            resultMap.put(entry.getKey(), entry.getValue());
        }
        for (String s : resultMap.keySet()) {
            sortTempMap.put(s, creteSortVO(map.get(s)));
        }
        return resultMap;
    }

    public Map<String, DriverCompSort> dealMap(Map<String, DriverCompSort> map) {
        Set<String> keySet = map.keySet();
        orgSet.removeAll(keySet);
        String year = null;
        String month = null;
        if(map.size() > 0){
            DriverCompSort driverCompSort = map.get(keySet.iterator().next());
            year = driverCompSort.getYear();
            month = driverCompSort.getMonth();
        }else {
            String[] time = timeUtil.getTime();
            year = time[0];
            month = String.valueOf(Integer.valueOf(time[1])-1);
        }
        for (String s : orgSet) {
            map.put(s, createDriverCompSort(s, year, month));
        }
        return map;
    }

    /**
     * 工具类，单独生成DriverCompSort类，
     */
    private DriverCompSort createDriverCompSort(String code, String year, String month) {
        DriverCompSort driverCompSort = new DriverCompSort();
        driverCompSort.setCompName(orgUtil.convetCode2Name(code));
        driverCompSort.setComp(code);
        driverCompSort.setMonth(month);
        driverCompSort.setYear(year);
        return driverCompSort;
    }

    /**
     * 用于生成SortVO中间对象
     */
    private SortVO creteSortVO(DriverCompSort driverCompSort) {
        SortVO sortVO = new SortVO();
        sortVO.setNum(driverCompSort.getNum());
        return sortVO;
    }

    /**
     * 该map存储每个分公司的排名名次，后续直接通过key,获取排名，再放到数据库对象里
     */
    public Map<String, Integer> sortMap(Map<String, DriverCompSort> map) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        Set<String> keySet = map.keySet();
        int count = 0;
        List<String> stringList = new ArrayList<>(keySet);
        for (int i = 0; i < stringList.size(); i++) {
            DriverCompSort driverCompSort = map.get(stringList.get(i));
            if (i == 0) {
                SortVO sortVO = sortTempMap.get(stringList.get(i));
                sortVO.setSort(1);
                sortTempMap.put(driverCompSort.getComp(), sortVO);
                count++;
            } else {
                SortVO  sortVO = sortTempMap.get(stringList.get(i - 1));
                if (sortVO.getNum() > driverCompSort.getNum()) {
                    sortTempMap.get(stringList.get(i)).setSort(sortVO.getSort() + count);
                    count = 0;
                    sortTempMap.put(driverCompSort.getComp(), sortTempMap.get(stringList.get(i)));
                } else {
                    sortTempMap.get(stringList.get(i)).setSort(sortVO.getSort());
                    count ++;
                    sortTempMap.put(driverCompSort.getComp(), sortTempMap.get(stringList.get(i)));
                }
            }
        }
        for (String s : sortTempMap.keySet()) {
            hashMap.put(s, sortTempMap.get(s).getSort());
        }
        return hashMap;
    }

    public List<DriverCompSort> createSqlList(Map<String, DriverCompSort> map) {
        Set<String> keySet = map.keySet();
        Map<String, Integer> sortMap = sortMap(map);
        List<DriverCompSort> list = new ArrayList<>();
        for (String s : keySet) {
            Integer sort = sortMap.get(s);
            DriverCompSort driverCompSort = map.get(s);
            driverCompSort.setSort(sort);
            list.add(driverCompSort);
        }
        return list;
    }

    private void write(List<DriverCompSort> list) {
        driverCompSortRepository.saveAll(list);
    }

    public void hand(Map<String, DriverCompSort> map) {
        Map<String, DriverCompSort> sortMap = dealMap(map);
        Map<String, DriverCompSort> compSortMap = sortFirstMap(sortMap);
        List<DriverCompSort> sqlList = createSqlList(compSortMap);
        write(sqlList);
    }
}
