package com.geekhalo.lego.core.singlequery.mybatis;

import com.geekhalo.lego.annotation.singlequery.MaxResultCheckStrategy;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MaxResultConfig {
    private final int maxResult;
    private final MaxResultCheckStrategy checkStrategy;
}
