package com.example.bysj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bysj.enity.Maps;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapsMapper extends BaseMapper<Maps> {

    //获取相关maps集合
    List<Maps> getList(@Param("istate") Integer istate);


    //根据关键字更新maps值
    void updateByKey(@Param("maps") Maps maps);

    //根据Key查询maps对象
    Maps readOneByKey(@Param("maps") Maps maps);
}
