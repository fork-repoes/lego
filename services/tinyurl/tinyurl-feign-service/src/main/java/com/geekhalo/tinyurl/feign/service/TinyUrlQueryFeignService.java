package com.geekhalo.tinyurl.feign.service;

import com.geekhalo.tinyurl.api.TinyUrlQueryApi;
import com.geekhalo.tinyurl.app.TinyUrlQueryApplicationService;
import com.geekhalo.tinyurl.domain.TinyUrl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(tags = "查询接口")
@RestController
@RequestMapping(TinyUrlQueryApi.PATH)
@Validated
public class TinyUrlQueryFeignService implements TinyUrlQueryApi {

    @Autowired
    private TinyUrlQueryApplicationService queryApplicationService;

    @GetMapping("queryByCode/{code}")
    @Override
    public String queryByCode(@PathVariable String code) {
        Optional<TinyUrl> tinyUrlOptional = this.queryApplicationService.findByCode(code);
        return tinyUrlOptional
                .filter(tinyUrl -> tinyUrl.canAccess())
                .map(tinyUrl -> tinyUrl.getUrl())
                .orElse(null);
    }

    @GetMapping("accessByCode/{code}")
    @Override
    public String accessByCode(String code) {
        Optional<TinyUrl> tinyUrlOptional = this.queryApplicationService.accessByCode(code);
        return tinyUrlOptional
                .filter(tinyUrl -> tinyUrl.canAccess())
                .map(tinyUrl -> tinyUrl.getUrl())
                .orElse(null);
    }
}
