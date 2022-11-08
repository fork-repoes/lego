package com.geekhalo.lego.feign;

import com.geekhalo.lego.DemoApplication;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * Created by taoli on 2022/11/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class, webEnvironment = DEFINED_PORT)
class TestFeignClientTest {
    @Autowired
    private TestFeignClient testFeignClient;
    @Autowired
    private TestFeignService testFeignService;

    private String key;
    private List<Long> data;

    @BeforeEach
    void setUp() {
        this.key = String.valueOf(RandomUtils.nextLong());
        this.data = Arrays.asList(RandomUtils.nextLong(), RandomUtils.nextLong(), RandomUtils.nextLong(), RandomUtils.nextLong());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void postData(){

        this.testFeignClient.postData(key, data);
        Assertions.assertEquals(data, this.testFeignService.getData(key));
    }

    @Test
    void postDataForError(){
        Assertions.assertThrows(Exception.class, ()->{
            this.testFeignClient.postDataForError(key, data);
        });
    }

    @Test
    void getData(){
        this.testFeignClient.postData(key, data);

        List<Long> data = this.testFeignService.getData(key);
        Assertions.assertEquals(data, this.data);

        List<Long> ds = this.testFeignClient.getData(key);
        Assertions.assertEquals(ds, this.data);
    }

    @Test
    void getDataForError(){
        this.testFeignClient.getData(key);
        Assertions.assertThrows(Exception.class, ()->{
            this.testFeignClient.getDataForError(key);
        });
    }
}