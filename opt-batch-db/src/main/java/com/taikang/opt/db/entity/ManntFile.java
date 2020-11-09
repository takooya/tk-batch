package com.taikang.opt.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 文件储存表
 *
 * @author itw_wangyc02
 * @date 2019/9/18 11:29
 */
@Entity
@Table(name = "mannt_file")
@Data
public class ManntFile {
    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileId;
    @Basic
    @Column(name = "file_name", nullable = false, length = 1)
    private String fileName;
    @Basic
    @Column(name = "file_base64", length = -1)
    private String fileBase64;
    @Basic
    @Column(name = "file_type", length = 1)
    private String fileType;
    @Basic
    @Column(name = "creator", nullable = false, length = 1)
    private String creator;
    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    @Basic
    @Column(name = "operate_statue", length = 1)
    private String operateStatue;
    @Basic
    @Column(name = "success_count")
    private Short successCount;
    @Basic
    @Column(name = "fail_count")
    private Short failCount;
    @Basic
    @Column(name = "file_size", length = 1)
    private String fileSize;
    @Basic
    @Column(name = "file_status", length = 1)
    private String fileStatus;
}
