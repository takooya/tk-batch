package com.taikang.opt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author itw_chenhn
 * @date 2019-10-30
 */
@Controller
@RequestMapping("/index")
public class indexController {
    @GetMapping
    public String index(){
        return "index";
    }
}
