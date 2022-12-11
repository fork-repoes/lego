package com.geekhalo.lego.enums.jpa;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.enums.NewsStatus;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/12/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
class JpaJpaNewsEntityRepositoryTest {
    @Autowired
    private JpaNewsEntityRepository jpaNewsEntityRepository;
    private List<JpaNewsEntity> newsEntities;

    @BeforeEach
    void setUp() {
        this.newsEntities = Lists.newArrayList();
        for (NewsStatus newsStatus : NewsStatus.values()){
            JpaNewsEntity news = new JpaNewsEntity();
            news.setStatus(newsStatus);
            this.newsEntities.add(news);
        }
        this.jpaNewsEntityRepository.saveAll(this.newsEntities);
    }

//    @AfterEach
//    void tearDown() {
//        this.newsEntityRepository.deleteAll();
//    }

    @Test
    void test(){
        this.newsEntities.forEach(jpaNewsEntity -> {
            Assertions.assertNotNull(jpaNewsEntity.getId());
        });


        Set<Long> ids = this.newsEntities.stream()
                .map(JpaNewsEntity::getId)
                .collect(Collectors.toSet());
        List<JpaNewsEntity> allById = this.jpaNewsEntityRepository.findAllById(ids);

        Assertions.assertFalse(allById.isEmpty());
        allById.forEach(jpaNewsEntity -> {
            Assertions.assertNotNull(jpaNewsEntity.getId());
            Assertions.assertNotNull(jpaNewsEntity.getStatus());
        });
    }
}