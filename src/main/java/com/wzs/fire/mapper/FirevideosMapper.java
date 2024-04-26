package com.wzs.fire.mapper;

import com.wzs.fire.pojo.entity.Firevideos;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FirevideosMapper {
    int deleteByPrimaryKey(Integer videoId);

    int insert(Firevideos record);

    int insertSelective(Firevideos record);

    Firevideos selectByPrimaryKey(Integer videoId);

    int updateByPrimaryKeySelective(Firevideos record);

    int updateByPrimaryKey(Firevideos record);

    @Select("select * from firevideos;")
    List<Firevideos> selectAll();
}