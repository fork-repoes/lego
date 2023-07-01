package com.geekhalo.tinyurl.domain;

import com.geekhalo.lego.core.command.support.AbstractAggRoot;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tiny_url")
//@Setter(AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
public class TinyUrl extends AbstractAggRoot {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private TinyUrlType type;

    @Enumerated(EnumType.STRING)
    private TinyUrlStatus status;

    @Column(name = "url", updatable = false)
    private String url;

    @Column(name = "max_count", updatable = false)
    private Integer maxCount;

    @Column(name = "access_count")
    private Integer accessCount;

    @Column(name = "begin_time", updatable = false)
    private Date beginTime;

    @Column(name = "expire_time", updatable = false)
    private Date expireTime;

    @Override
    public Long getId() {
        return this.id;
    }

    public static TinyUrl createTinyUrl(CreateTinyUrlContext context){
        TinyUrl tinyUrl = new TinyUrl();

        tinyUrl.setId(context.nextId());
        tinyUrl.setType(TinyUrlType.COMMON);
        tinyUrl.setStatus(TinyUrlStatus.ENABLE);

        tinyUrl.setUrl(context.getCommand().getUrl());

        return tinyUrl;
    }

    public static TinyUrl createExpireTimeTinyUrl(CreateExpireTimeTinyUrlContext context){
        TinyUrl tinyUrl = new TinyUrl();

        tinyUrl.setId(context.nextId());
        tinyUrl.setType(TinyUrlType.EXPIRE_TIME);
        tinyUrl.setStatus(TinyUrlStatus.ENABLE);

        tinyUrl.setUrl(context.getCommand().getUrl());
        tinyUrl.setExpireTime(context.getCommand().getExpireTime());
        tinyUrl.setBeginTime(context.getCommand().parseBeginTime());

        return tinyUrl;
    }

    public static TinyUrl createLimitTimeTinyUrl(CreateLimitTimeTinyUrlContext context){
        TinyUrl tinyUrl = new TinyUrl();

        tinyUrl.setId(context.nextId());
        tinyUrl.setType(TinyUrlType.LIMIT_TIME);
        tinyUrl.setStatus(TinyUrlStatus.ENABLE);

        tinyUrl.setUrl(context.getCommand().getUrl());
        tinyUrl.setMaxCount(context.getCommand().getMaxCount());
        tinyUrl.setAccessCount(0);

        return tinyUrl;
    }

    public void incrAccessCount(IncrAccessCountCommand command){
        int incrCount = command.incrCount();

        incrAccessCount(incrCount);
    }

    public void incrAccessCount(int incrCount) {
        Integer accessCount = getAccessCount();
        if (getType().needUpdateAccessCount()){
            Integer accessCountNew = accessCount + incrCount;
            setAccessCount(accessCountNew);
        }
    }

    public void disable(DisableTinyUrlCommand command){
        if (getStatus() == TinyUrlStatus.ENABLE){
            setStatus(TinyUrlStatus.DISABLE);
        }
    }

    @Override
    public void validate() {
        super.validate();
        Preconditions.checkArgument(getType() != null);
        Preconditions.checkArgument(getStatus() != null);
        Preconditions.checkArgument(StringUtils.isNotEmpty(getUrl()));
        this.type.validate(this);
    }

    public boolean canAccess(){
        return this.type.canAccess(this);
    }

    public boolean needUpdateAccessCount(){
        return this.type.needUpdateAccessCount();
    }


    boolean checkTime() {
        return new Date().after(getBeginTime()) && new Date().before(getExpireTime());
    }

    boolean checkCount() {
        return getAccessCount() < getMaxCount();
    }

    boolean checkStatus() {
        return TinyUrlStatus.ENABLE == getStatus();
    }
}
