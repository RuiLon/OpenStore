package com.example.bysj.service.imp;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bysj.enity.Maps;
import com.example.bysj.mapper.MapsMapper;
import com.example.bysj.service.infc.MapsInfc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MapsServiceImp extends ServiceImpl<MapsMapper, Maps> implements MapsInfc {


        @Autowired
        private MapsMapper mapsMapper;


        //初始化各个maps
        public void  setMaps(int istate, Map<String,Integer> maps)
        {
            List<Maps> list = this.mapsMapper.getList(istate);
            for (Maps map:list)
            {
                maps.put(map.getSkey(),map.getIvalue());
            }
        }

        //将maps最近的值储存
        public void addMaps(Maps maps)
        {
            this.mapsMapper.insert(maps);
        }

        //更新maps最近关键字的值
        public void updateMaps(Maps maps)
        {
            this.mapsMapper.updateByKey(maps);
        }



}
