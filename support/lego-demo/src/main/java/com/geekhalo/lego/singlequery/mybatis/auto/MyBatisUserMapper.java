package com.geekhalo.lego.singlequery.mybatis.auto;

import com.geekhalo.lego.singlequery.mybatis.auto.MyBatisUser;
import com.geekhalo.lego.singlequery.mybatis.auto.MyBatisUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MyBatisUserMapper {
    long countByExample(MyBatisUserExample example);

    int deleteByExample(MyBatisUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MyBatisUser row);

    int insertSelective(MyBatisUser row);

    List<MyBatisUser> selectByExample(MyBatisUserExample example);

    MyBatisUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") MyBatisUser row, @Param("example") MyBatisUserExample example);

    int updateByExample(@Param("row") MyBatisUser row, @Param("example") MyBatisUserExample example);

    int updateByPrimaryKeySelective(MyBatisUser row);

    int updateByPrimaryKey(MyBatisUser row);
}