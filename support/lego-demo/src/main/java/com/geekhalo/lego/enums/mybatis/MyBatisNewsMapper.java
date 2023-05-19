package com.geekhalo.lego.enums.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by taoli on 2022/12/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface MyBatisNewsMapper {
    @Insert("insert t_mybatis_news (status) value (#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(MyBatisNewsEntity entity);

    @Select("select id, status from t_mybatis_news where id = #{id}")
    List<MyBatisNewsEntity> findById(Long id);
}
