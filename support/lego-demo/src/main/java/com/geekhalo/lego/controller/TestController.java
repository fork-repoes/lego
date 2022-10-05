package com.geekhalo.lego.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by taoli on 2022/10/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("test")
    public String test(){
        return "SUCCESS";
    }
}
