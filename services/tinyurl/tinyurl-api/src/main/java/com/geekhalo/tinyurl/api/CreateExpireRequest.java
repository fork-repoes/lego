package com.geekhalo.tinyurl.api;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateExpireRequest {
    @NotEmpty
    private String url;

    private Boolean enableCache;

    private Boolean enableCacheSync;

    private Date beginTime;

    @NotNull
    private Date expireTime;
}
