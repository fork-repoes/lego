package com.geekhalo.tinyurl.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateExpireTimeTinyUrlCommand extends AbstractCreateTinyUrlCommand{
    private Date beginTime;

    @NotNull
    private Date expireTime;

    public Date parseBeginTime(){
        return beginTime == null ? new Date() : beginTime;
    }
}
