package com.geekhalo.lego.enums.mybatis;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.enums.NewsStatus;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Created by taoli on 2022/12/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
class MyBatisNewsMapperTest {
    private List<MyBatisNewsEntity> newsEntities;
    @Autowired
    private MyBatisNewsMapper myBatisNewsMapper;

    @BeforeEach
    void setUp() {
        this.newsEntities = Lists.newArrayList();
        for (NewsStatus newsStatus : NewsStatus.values()){
            MyBatisNewsEntity news = new MyBatisNewsEntity();
            news.setStatus(newsStatus);
            this.newsEntities.add(news);
            this.myBatisNewsMapper.save(news);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void save() {
    }

    @Test
    void findById() {
        this.newsEntities.forEach(newsEntity -> {
            Assertions.assertNotNull(newsEntity.getId());
            List<MyBatisNewsEntity> byId = this.myBatisNewsMapper.findById(newsEntity.getId());
            Assertions.assertFalse(byId.isEmpty());
            byId.forEach(newsEntity1 -> {
                Assertions.assertNotNull(newsEntity1.getId());
                Assertions.assertNotNull(newsEntity1.getStatus());
            });
        });
    }
}