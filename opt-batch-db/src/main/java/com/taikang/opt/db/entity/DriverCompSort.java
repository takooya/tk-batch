package com.taikang.opt.db.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author itw_chenhn
 */
@Entity
@Table(name = "driver_comp_sort", schema = "tkquotemaptest", catalog = "")
@IdClass(DriverCompSortPK.class)
@Data
public class DriverCompSort {
    @Id
    @Column(name = "comp")
    private String comp;
    @Id
    @Column(name = "month")
    private String month;
    @Id
    @Column(name = "year")
    private String year;
    @Column(name = "sort")
    private int sort;
    @Column(name = "comp_name")
    private String compName;
    @Column(name = "num")
    private int num;



}
