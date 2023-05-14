package com.geekhalo.like.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.PRIVATE)
public class ActionTarget {
    private Integer type;
    private Long id;
}
