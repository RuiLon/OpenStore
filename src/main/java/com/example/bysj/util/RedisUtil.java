package com.example.bysj.util;

import com.example.bysj.enity.Maps;
import com.example.bysj.service.imp.MapsServiceImp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class RedisUtil {

    //定义流水增量常量
    private static int delta = 1;

    public String incr(Map<String,Integer> map,int istate,MapsServiceImp mapsServiceImp){

        //创建一个空maps对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String key = simpleDateFormat.format( new Date());
        Maps maps = new Maps();
        maps.setSkey(key);
        maps.setIstate(istate);
        String result= key;
        int num=0;
        //判断map容器是否包含当前Key
        //如果包含，则更新当前带有当前Key的Maps对象中的value值
        //反之，则向数据库插入一个Maps对象
        if(map.containsKey(key))
        {
            num = map.get(key);
            num=num+delta;
            map.replace(key,num);
            maps.setIvalue(num);
            mapsServiceImp.updateMaps(maps);
        }
        else
        {

            num = num+delta;
            map.put(key,num);
            maps.setIvalue(num);
            mapsServiceImp.addMaps(maps);
        }
        int i=1000;

        while( (num/i) ==0)
        {
            result = result + '0';
            i=i/10;
        }

        result = result + String.valueOf(num);
        return result;
    }


}
