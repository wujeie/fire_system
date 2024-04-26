package com.wzs.fire.mapper;

import com.wzs.fire.pojo.entity.Testpapers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestpapersMapper {
    int deleteByPrimaryKey(Integer testpaperId);

    int insert(Testpapers record);

    int insertSelective(Testpapers record);

    Testpapers selectByPrimaryKey(Integer testpaperId);

    int updateByPrimaryKeySelective(Testpapers record);

    int updateByPrimaryKey(Testpapers record);

    @Select("select * from testpapers")
    List<Testpapers> getAllpapers();

}