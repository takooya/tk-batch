package com.taikang.opt.db.repository;

import com.taikang.opt.db.entity.ManntFile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ManntFileRepository extends JpaRepository<ManntFile, Integer> {
    /**
     * 根据创建人搜索文件储存列表
     *
     * @param creator :创建人loginId
     * @return : 文件储存列表数据
     * @author itw_wangyc02
     * @date 2019/9/18 15:44
     */
    @Query(value = "select mf.* from mannt_file mf where mf.creator = :creator order by mf.create_time desc ",
            nativeQuery = true)
    List<ManntFile> findByCreator(@Param("creator") String creator);

    /**
     * 根据文件id和创建人搜索文件
     *
     * @param fileId  :文件id
     * @param creator :创建人loginId
     * @return : 文件储存列表数据
     * @author itw_wangyc02
     * @date 2019/9/18 15:44
     */
    ManntFile findByFileIdAndAndCreator(Integer fileId, String creator);

    @Query(value = "SELECT * FROM mannt_file WHERE create_time BETWEEN ?1 AND ?2",nativeQuery = true)
    List<ManntFile> findbyTime(String startTime,String endTime);
}
