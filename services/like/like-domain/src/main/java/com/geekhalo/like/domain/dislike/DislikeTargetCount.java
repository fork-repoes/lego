package com.geekhalo.like.domain.dislike;

import com.geekhalo.like.domain.AbstractTargetCount;
import com.geekhalo.like.domain.ActionTarget;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dislike_target_count")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DislikeTargetCount extends AbstractTargetCount {
    public static DislikeTargetCount create(ActionTarget target) {
        DislikeTargetCount targetCount = new DislikeTargetCount();
        targetCount.init(target, 0L);
        return targetCount;
    }
}
