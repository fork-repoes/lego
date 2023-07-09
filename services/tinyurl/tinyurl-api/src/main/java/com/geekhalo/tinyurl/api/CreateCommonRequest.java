package com.geekhalo.tinyurl.api;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateCommonRequest {
    @NotEmpty
    private String url;

    private Boolean enableCache;

    private Boolean enableCacheSync;
}
