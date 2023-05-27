package com.geekhalo.like.feign.service;

import com.geekhalo.like.api.ActionCommandApi;
import com.geekhalo.like.api.ActionCommandParam;
import com.geekhalo.like.app.ActionCommandApplicationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "操作接口")
@RestController
@RequestMapping(ActionCommandApi.PATH)
@Validated
public class ActionCommandFeignService implements ActionCommandApi {
    @Autowired
    private ActionCommandApplicationService commandApplicationService;

    @Override
    @PostMapping("like")
    public void like(@RequestBody @Valid ActionCommandParam param){
        this.commandApplicationService.like(param.getUserId(), param.getTargetType(), param.getTargetId());
    }

    @Override
    @PostMapping("unlike")
    public void unLike(@RequestBody @Valid ActionCommandParam param){
        this.commandApplicationService.unLike(param.getUserId(), param.getTargetType(), param.getTargetId());
    }

    @Override
    @PostMapping("dislike")
    public void dislike(@RequestBody @Valid ActionCommandParam param){
        this.commandApplicationService.dislike(param.getUserId(), param.getTargetType(), param.getTargetId());
    }

    @Override
    @PostMapping("unDislike")
    public void unDislike(@RequestBody @Valid ActionCommandParam param){
        this.commandApplicationService.unDislike(param.getUserId(), param.getTargetType(), param.getTargetId());
    }

}
