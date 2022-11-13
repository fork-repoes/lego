package com.geekhalo.lego.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by taoli on 2022/11/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@FeignClient(name = "testFeignClient", url = "http://127.0.0.1:9090")
public interface TestFeignClient extends TestFeignApi{
}
