package com.geekhalo.lego.singlequery.mybatis.auto;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyBatisUserMapper {
    long countByExample(MyBatisUserExample example);

    int deleteByExample(MyBatisUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MyBatisUser record);

    int insertSelective(MyBatisUser record);

    List<MyBatisUser> selectByExample(MyBatisUserExample example);

    MyBatisUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MyBatisUser record, @Param("example") MyBatisUserExample example);

    int updateByExample(@Param("record") MyBatisUser record, @Param("example") MyBatisUserExample example);

    int updateByPrimaryKeySelective(MyBatisUser record);

    int updateByPrimaryKey(MyBatisUser record);
}