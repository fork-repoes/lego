package com.geekhalo.like.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ActionCommandApi {
    String PATH = "feignService/action/command";


    @PostMapping("like")
    void like(@RequestParam Long userId, @RequestParam Integer targetType, @RequestParam Long targetId);

    @PostMapping("unlike")
    void unLike(@RequestParam Long userId, @RequestParam Integer targetType, @RequestParam Long targetId);

    @PostMapping("dislike")
    void dislike(@RequestParam Long user, @RequestParam Integer targetType, @RequestParam Long targetId);

    @PostMapping("unDislike")
    void unDislike(@RequestParam Long userId, @RequestParam Integer targetType, @RequestParam Long targetId);
}
