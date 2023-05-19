package com.geekhalo.like.domain.target;

public interface SingleActionTargetLoader {
    boolean support(String type);

    ActionTarget load(String type, Long id);
}
