package com.taikang.opt.util;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author  itw_chenhn
 * 工具获取类
 */
@Component
public class TimeUtil {
    /**
     *    工具类，根据type类型，返回string 时间
     *    0----当前时间
     *    14----前14天的时间（2周）
     *    30----前一个月的时间
     *    1-----前1天的时间
     * */
    public String getTime(int type){
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       /* switch (type){
            case 0:
                break;
            case 1:
                instance.set(Calendar.DATE,instance.get(Calendar.DATE)-1);
            case 14:
                instance.set(Calendar.DATE,instance.get(Calendar.DATE)-14);
                break;
            case 30:
                instance.set(Calendar.DATE,instance.get(Calendar.DATE)-30);
                break;
        }*/
       instance.set(Calendar.DATE,instance.get(Calendar.DATE)-type);
        String time = dateFormat.format(instance.getTime());
        return time;
    }
    /**
     *   用来获取时间
     * */
    public Date getDate(Integer type){
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.set(Calendar.DATE,instance.get(Calendar.DATE)-type);
        return instance.getTime();
    }
    /**
     *  用来获得年，前一个月的年，月
     * */
    public String[] getTime(){
        String time = getTime(0);
        String[] timeSplit = time.split("-");
        return timeSplit;
    }
    /**
     *   获得当前时间的前一个月的范围时间
     * */
    public String[] getBeforeTime(){
        String[] time = new String[2];
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH,-1);
        instance.set(Calendar.DAY_OF_MONTH,1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String firstDay = dateFormat.format(instance.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,0);
        String lastTime = dateFormat.format(calendar.getTime());
        time[0] = firstDay;
        time[1] = lastTime;
        return time;
    }
}
