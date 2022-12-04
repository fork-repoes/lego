package com.geekhalo.lego.core.bitop.news;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by taoli on 2022/12/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class NewsTests {
    private News news = new News();

    @Test
    void testSystem(){
        Assertions.assertFalse(news.isShow());
        news.setSystem();
        Assertions.assertTrue(news.isShow());

        news.setBlackList();
        Assertions.assertFalse(news.isShow());
    }

    @Test
    void testCommonUser(){
        Assertions.assertFalse(news.isShow());
        news.setCommonUser();
        Assertions.assertFalse(news.isShow());
        news.passAudit();
        Assertions.assertTrue(news.isShow());

        news.setBlackList();
        Assertions.assertFalse(news.isShow());
    }

    @Test
    void testVipUser(){
        Assertions.assertFalse(news.isShow());
        news.setVipUser();
        Assertions.assertFalse(news.isShow());
        news.submitAudit();
        Assertions.assertTrue(news.isShow());
        news.passAudit();
        Assertions.assertTrue(news.isShow());

        news.setBlackList();
        Assertions.assertFalse(news.isShow());
    }

    @Test
    void testWhite(){
        Assertions.assertFalse(news.isShow());
        news.setWhiteList();
        Assertions.assertTrue(news.isShow());

        news.setBlackList();
        Assertions.assertFalse(news.isShow());
    }

    @Test
    void testSqlFilter(){
        System.out.println(this.news.getSqlFilter("status"));
    }

}
