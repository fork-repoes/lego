package com.geekhalo.like.domain.like;

import com.geekhalo.like.domain.AbstractTargetCount;
import com.geekhalo.like.domain.target.ActionTarget;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "like_target_count")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LikeTargetCount extends AbstractTargetCount {

    public static LikeTargetCount create(ActionTarget target){
        return create(target, 0L);
    }
    public static LikeTargetCount create(ActionTarget target, Long count) {
        LikeTargetCount likeTargetCount = new LikeTargetCount();
        likeTargetCount.init(target, count);
        return likeTargetCount;
    }

}
