package com.example.bysj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bysj.enity.Borret;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BorretMapper extends BaseMapper<Borret> {


    void updateBorret(@Param("borret") Borret borret);
}
