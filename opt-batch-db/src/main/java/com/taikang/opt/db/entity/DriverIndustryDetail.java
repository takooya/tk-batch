package com.taikang.opt.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author itw_chenhn
 */
@Entity
@Table(name = "driver_industry_detail", schema = "tkquotemaptest", catalog = "")
@Data
public class DriverIndustryDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "industry")
    private String industry;
    @Column(name = "total_ct")
    private int totalCt;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "focus_ct")
    private int focusCt;
    @Column(name = "industry_name")
    private String industryName;
    @Column(name = "is_focus_track")
    private String isFocusTrack;

}
