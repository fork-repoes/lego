package com.geekhalo.like;

import com.geekhalo.lego.core.validator.ValidateException;
import com.geekhalo.like.api.*;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = LikeApplication.class)
public class ActionTest {
    private Long userId;
    private String targetType;
    private Long targetId;

    @Autowired
    private ActionCommandApi actionCommandApi;
    @Autowired
    private ActionQueryApi actionQueryApi;
    @Autowired
    private TargetCountQueryApi targetCountQueryApi;

    @BeforeEach
    public void init(){
        this.targetType = "Test";
        this.userId = RandomUtils.nextLong(0, Long.MAX_VALUE);
        this.targetId = RandomUtils.nextLong(0, Long.MAX_VALUE);
    }

    @Test
    public void likeTest() throws Exception{
        List<ActionVO> likeByUserAndType = this.actionQueryApi.getLikeByUserAndType(this.userId, this.targetType);
        List<TargetCountVO> likeCountByTarget = this.targetCountQueryApi.getLikeCountByTarget(this.targetType, Arrays.asList(this.targetId));
        TargetCountVO targetCountVO = likeCountByTarget.stream()
                .filter(targetCountVO1 -> targetCountVO1.getTargetId().equals(this.targetId))
                .findFirst()
                .orElseGet(() -> {
                    TargetCountVO nullTargetCount = new TargetCountVO();
                    nullTargetCount.setTargetId(this.targetId);
                    nullTargetCount.setTargetType(this.targetType);
                    nullTargetCount.setCount(0L);
                    return nullTargetCount;
                });
        ActionCommandParam param = new ActionCommandParam(this.userId, this.targetType, this.targetId);
        TimeUnit.SECONDS.sleep(1);
        this.actionCommandApi.like(param);
        {
            List<ActionVO> likeByUserAndTypeAfterLike = this.actionQueryApi.getLikeByUserAndType(this.userId, this.targetType);
            Assert.assertEquals(likeByUserAndType.size() + 1, likeByUserAndTypeAfterLike.size());

            List<TargetCountVO> likeCountByTargetAfterLike = this.targetCountQueryApi.getLikeCountByTarget(this.targetType, Arrays.asList(this.targetId));
            TargetCountVO targetCountVOAfterUnlike = likeCountByTargetAfterLike.stream()
                    .filter(targetCountVO1 -> targetCountVO1.getTargetId().equals(this.targetId))
                    .findFirst()
                    .orElseGet(() -> {
                        TargetCountVO nullTargetCount = new TargetCountVO();
                        nullTargetCount.setTargetId(this.targetId);
                        nullTargetCount.setTargetType(this.targetType);
                        nullTargetCount.setCount(0L);
                        return nullTargetCount;
                    });
            Assert.assertEquals(targetCountVO.getCount() + 1L, targetCountVOAfterUnlike.getCount() + 0L);
        }

        this.actionCommandApi.unLike(param);
        TimeUnit.SECONDS.sleep(1);
        {
            List<ActionVO> likeByUserAndTypeAfterUnlike = this.actionQueryApi.getLikeByUserAndType(this.userId, this.targetType);
            Assert.assertEquals(likeByUserAndType.size() + 1, likeByUserAndTypeAfterUnlike.size());

            List<TargetCountVO> likeCountByTargetAfterUnlike = this.targetCountQueryApi.getLikeCountByTarget(this.targetType, Arrays.asList(this.targetId));
            TargetCountVO targetCountVOAfterUnlike = likeCountByTargetAfterUnlike.stream()
                    .filter(targetCountVO1 -> targetCountVO1.getTargetId().equals(this.targetId))
                    .findFirst()
                    .orElseGet(() -> {
                        TargetCountVO nullTargetCount = new TargetCountVO();
                        nullTargetCount.setTargetId(this.targetId);
                        nullTargetCount.setTargetType(this.targetType);
                        nullTargetCount.setCount(0L);
                        return nullTargetCount;
                    });
            Assert.assertEquals(targetCountVO.getCount(), targetCountVOAfterUnlike.getCount());
        }

        for (int i = 0; i< 100; i ++){
            this.targetCountQueryApi.getLikeCountByTarget(this.targetType, Arrays.asList(this.targetId));
        }
    }

    @Test
    public void dislikeTest() throws Exception{
        List<ActionVO> dislikeByUserAndType = this.actionQueryApi.getDislikeByUserAndType(this.userId, this.targetType);
        List<TargetCountVO> dislikeCountByTarget = this.targetCountQueryApi.getDislikeCountByType(this.targetType, Arrays.asList(this.targetId));
        TargetCountVO targetCountVO = dislikeCountByTarget.stream()
                .filter(targetCountVO1 -> targetCountVO1.getTargetId().equals(this.targetId))
                .findFirst()
                .orElseGet(() -> {
                    TargetCountVO nullTargetCount = new TargetCountVO();
                    nullTargetCount.setTargetId(this.targetId);
                    nullTargetCount.setTargetType(this.targetType);
                    nullTargetCount.setCount(0L);
                    return nullTargetCount;
                });

        ActionCommandParam param = new ActionCommandParam(this.userId, this.targetType, this.targetId);

        this.actionCommandApi.dislike(param);
        TimeUnit.SECONDS.sleep(1);

        {
            List<ActionVO> dislikeByUserAndTypeAfterDislike = this.actionQueryApi.getDislikeByUserAndType(this.userId, this.targetType);
            Assert.assertEquals(dislikeByUserAndType.size() + 1, dislikeByUserAndTypeAfterDislike.size());

            List<TargetCountVO> dislikeCountByTargetAfterDislike = this.targetCountQueryApi.getDislikeCountByType(this.targetType, Arrays.asList(this.targetId));
            TargetCountVO targetCountVOAfterDislike = dislikeCountByTargetAfterDislike.stream()
                    .filter(targetCountVO1 -> targetCountVO1.getTargetId().equals(this.targetId))
                    .findFirst()
                    .orElseGet(() -> {
                        TargetCountVO nullTargetCount = new TargetCountVO();
                        nullTargetCount.setTargetId(this.targetId);
                        nullTargetCount.setTargetType(this.targetType);
                        nullTargetCount.setCount(0L);
                        return nullTargetCount;
                    });
            Assert.assertEquals(targetCountVO.getCount() + 1L, targetCountVOAfterDislike.getCount() + 0L);
        }

        this.actionCommandApi.unDislike(param);
        TimeUnit.SECONDS.sleep(1);
        {
            List<ActionVO> likeByUserAndTypeAfterUndislike = this.actionQueryApi.getDislikeByUserAndType(this.userId, this.targetType);
            Assert.assertEquals(dislikeByUserAndType.size() + 1, likeByUserAndTypeAfterUndislike.size());

            List<TargetCountVO> likeCountByTargetAfterUnDislike = this.targetCountQueryApi.getDislikeCountByType(this.targetType, Arrays.asList(this.targetId));
            TargetCountVO targetCountVOAfterUnDislike = likeCountByTargetAfterUnDislike.stream()
                    .filter(targetCountVO1 -> targetCountVO1.getTargetId().equals(this.targetId))
                    .findFirst()
                    .orElseGet(() -> {
                        TargetCountVO nullTargetCount = new TargetCountVO();
                        nullTargetCount.setTargetId(this.targetId);
                        nullTargetCount.setTargetType(this.targetType);
                        nullTargetCount.setCount(0L);
                        return nullTargetCount;
                    });
            Assert.assertEquals(targetCountVO.getCount(), targetCountVOAfterUnDislike.getCount());
        }
        for (int i = 0; i< 100; i ++){
            this.targetCountQueryApi.getDislikeCountByType(this.targetType, Arrays.asList(this.targetId));
        }
    }

    @Test
    public void actionUserTest(){
        ActionCommandParam param = new ActionCommandParam(-this.userId, this.targetType, this.targetId);
        Assert.assertThrows(ValidateException.class, () -> this.actionCommandApi.like(param));
    }

    @Test
    public void actionTargetTest(){
        ActionCommandParam param = new ActionCommandParam(this.userId, this.targetType, -this.targetId);
        Assert.assertThrows(ValidateException.class, () -> this.actionCommandApi.like(param));
    }
}
