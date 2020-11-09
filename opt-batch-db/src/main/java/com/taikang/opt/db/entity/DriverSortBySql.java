package com.taikang.opt.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author itw_chenhn
 */
@Entity
@Data
public class DriverSortBySql {
    @Id
    @Column(name = "comp")
    private String comp;
    @Column(name = "num")
    private int num;
}
