package com.geekhalo.like.feign.service;

import com.geekhalo.like.api.ActionCommandApi;
import com.geekhalo.like.app.ActionCommandApplicationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "操作接口")
@RestController
@RequestMapping(ActionCommandApi.PATH)
public class ActionCommandFeignService implements ActionCommandApi {
    @Autowired
    private ActionCommandApplicationService commandApplicationService;

    @Override
    @PostMapping("like")
    public void like(@RequestParam Long userId, @RequestParam String targetType, @RequestParam Long targetId){
        this.commandApplicationService.like(userId, targetType, targetId);
    }

    @Override
    @PostMapping("unlike")
    public void unLike(@RequestParam Long userId, @RequestParam String targetType, @RequestParam Long targetId){
        this.commandApplicationService.unLike(userId, targetType, targetId);
    }

    @Override
    @PostMapping("dislike")
    public void dislike(@RequestParam Long user, @RequestParam String targetType, @RequestParam Long targetId){
        this.commandApplicationService.dislike(user, targetType, targetId);
    }

    @Override
    @PostMapping("unDislike")
    public void unDislike(@RequestParam Long userId, @RequestParam String targetType, @RequestParam Long targetId){
        this.commandApplicationService.unDislike(userId, targetType, targetId);
    }

}
