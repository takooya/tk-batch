package com.taikang.opt.service;

import com.taikang.opt.Enum.ManntFileTypeEnum;
import com.taikang.opt.db.entity.ManntFile;
import com.taikang.opt.db.repository.ManntFileRepository;
import com.taikang.opt.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author itw_chenhn
 * @date 2019-10-10
 */
@Service
public class ManntFileService {
    private final ManntFileRepository manntFileRepository;
    private final TimeUtil timeUtil;

    public ManntFileService(ManntFileRepository manntFileRepository, TimeUtil timeUtil) {
        this.manntFileRepository = manntFileRepository;
        this.timeUtil = timeUtil;
    }

    public void updateManntFile(){
        String endTime = timeUtil.getTime(0);
        String startTime = timeUtil.getTime(30);
        List<ManntFile> manntFiles = manntFileRepository.findbyTime(startTime, endTime);
        for (ManntFile manntFile : manntFiles) {
            /**
             *  base64置为null，删除
             * */
            manntFile.setFileBase64(null);
            /**
             *   fileStatus 置为 3 代表过期
             * */
            manntFile.setFileStatus(ManntFileTypeEnum.PASS.getCode());
            /**
             *   OperateStatue  置为已过期
             * */
            manntFile.setOperateStatue(ManntFileTypeEnum.PASS.getMessage());
        }
        manntFileRepository.saveAll(manntFiles);
    }

}
