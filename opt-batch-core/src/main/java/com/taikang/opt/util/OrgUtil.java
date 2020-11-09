package com.taikang.opt.util;

import org.springframework.stereotype.Component;

/**
 * @author itw_chenhn
 */
@Component
public class OrgUtil {
    public  String convetCode2Name(String code){
        String value = "";
        switch (code){
            case "51":
                value = "北京";
                break;
            case "52":
                value = "湖北";
                break;
            case "53":
                value = "广东";
                break;
            case "55":
                value = "四川";
                break;
            case "56":
                value = "辽宁";
                break;
            case "57":
                value = "江苏";
                break;
            case "58":
                value = "河南";
                break;
            case "59":
                value = "浙江";
                break;
            case "60":
                value = "山东";
                break;
            case "61":
                value = "天津";
                break;
            case "62":
                value = "陕西";
                break;
            case "63":
                value = "重庆";
                break;
            case "64":
                value = "福建";
                break;
            case "65":
                value = "湖南";
                break;
            case "66":
                value = "深圳";
                break;
            case "67":
                value = "安徽";
                break;
            case "68":
                value = "大连";
                break;
            case "69":
                value = "青岛";
                break;
            case "70":
                value = "宁波";
                break;
            case "71":
                value = "河北";
                break;
            case "72":
                value = "黑龙江";
                break;
            case "73":
                value = "云南";
                break;
            case "74":
                value = "山西";
                break;
            case "75":
                value = "广西";
                break;
            case "76":
                value = "吉林";
                break;
            case "77":
                value = "江西";
                break;
            case "78":
                value = "新疆";
                break;
            case "79":
                value = "厦门";
                break;
            case "81":
                value = "内蒙古";
                break;
            case "82":
                value = "贵州";
                break;
            case "83":
                value = "甘肃";
                break;
            case "84":
                value = "上海";
                break;
            case "86":
                value = "宁夏";
                break;
            case "97":
                value = "西藏";
                break;
            case "98":
                value = "青海";
                break;
            case "99":
                value = "海南";
                break;
            case "%":
                value="总公司";
                break;
            default:
                break;
        }
        return value;
    }

    public String convert2Category(String code){
        String value = "";
        switch (code){
            case "51":
                value = "战略A";
                break;
            case "52":
                value = "A";
                break;
            case "53":
                value = "战略A";
                break;
            case "55":
                value = "A";
                break;
            case "56":
                value = "B";
                break;
            case "57":
                value = "战略A";
                break;
            case "58":
                value = "A";
                break;
            case "59":
                value = "战略A";
                break;
            case "60":
                value = "A";
                break;
            case "61":
                value = "C";
                break;
            case "62":
                value = "B";
                break;
            case "63":
                value = "B";
                break;
            case "64":
                value = "B";
                break;
            case "65":
                value = "B";
                break;
            case "66":
                value = "战略A";
                break;
            case "67":
                value = "B";
                break;
            case "68":
                value = "C";
                break;
            case "69":
                value = "C";
                break;
            case "70":
                value = "C";
                break;
            case "71":
                value = "A";
                break;
            case "72":
                value = "C";
                break;
            case "73":
                value = "C";
                break;
            case "74":
                value = "A";
                break;
            case "75":
                value = "B";
                break;
            case "76":
                value = "C";
                break;
            case "77":
                value = "B";
                break;
            case "78":
                value = "B";
                break;
            case "79":
                value = "C";
                break;
            case "81":
                value = "C";
                break;
            case "82":
                value = "C";
                break;
            case "83":
                value = "B";
                break;
            case "84":
                value = "战略A";
                break;
            case "86":
                value = "C";
                break;
            case "97":
                value = " ";
                break;
            case "98":
                value = " ";
                break;
            case "99":
                value = " ";
                break;
            case "%":
                value=" ";
                break;
            default:
                break;
        }
        return value;
    }
}
