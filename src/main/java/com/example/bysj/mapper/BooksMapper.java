package com.example.bysj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bysj.enity.Books;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksMapper extends BaseMapper<Books> {

    //更新书籍数据
    void updateOne(@Param("books") Books books);

    //删除当前数据
    void delbooks(@Param("books") Books books);

    //批量删除数据
    void delList(@Param("delList") List<Books> delList);

    //获取当前书本信息
    Books selectOne(@Param("bid") Integer bid);
}
