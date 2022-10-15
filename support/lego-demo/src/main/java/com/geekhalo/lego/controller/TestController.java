package com.geekhalo.lego.controller;

import com.geekhalo.lego.query.GetByUserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Created by taoli on 2022/10/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    @GetMapping("test")
    public String test(){
        return "SUCCESS";
    }

    @PostMapping("param")
    public String param(@RequestParam Long id, GetByUserId getByUserId){
        log.info("id {}, getByUserId {}", id, getByUserId);
        return "SUCCESS";
    }

    @PostMapping("body")
    public String body(@RequestBody GetByUserId getByUserId){
        log.info("getByUserId {}", getByUserId);
        return "SUCCESS";
    }
}
