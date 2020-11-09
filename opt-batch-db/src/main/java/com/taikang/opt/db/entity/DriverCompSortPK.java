package com.taikang.opt.db.entity;


import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author itw_chenhn
 */
public class DriverCompSortPK implements Serializable {
    @Id
    @Column
    private String comp;
    @Id
    @Column
    private String month;
    @Id
    @Column
    private String year;
}
