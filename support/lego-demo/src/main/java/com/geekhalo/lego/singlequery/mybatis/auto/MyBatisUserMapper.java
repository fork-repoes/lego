package com.geekhalo.lego.singlequery.mybatis.auto;

import com.geekhalo.lego.singlequery.mybatis.auto.MyBatisUser;
import com.geekhalo.lego.singlequery.mybatis.auto.MyBatisUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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