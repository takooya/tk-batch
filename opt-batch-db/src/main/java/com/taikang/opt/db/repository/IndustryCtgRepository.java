package com.taikang.opt.db.repository;

import com.taikang.opt.db.entity.IndustCtg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author itw_yinjm
 * @date 2018/10/24
 */
public interface IndustryCtgRepository extends JpaRepository<IndustCtg, String> {
    List<IndustCtg> findAllByParentCode(String code);
}
