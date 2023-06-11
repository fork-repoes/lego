package com.geekhalo.tinyurl.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateExpireTimeTinyUrlCommand extends AbstractCreateTinyUrlCommand{
    @NotNull
    private Date expireTime;
}
