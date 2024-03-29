package com.geekhalo.feed.domain.feed;

import com.geekhalo.feed.domain.feed.create.CreateFeedContext;
import com.geekhalo.feed.domain.feed.create.FeedCreatedEvent;
import com.geekhalo.feed.domain.feed.disable.DisableFeedContext;
import com.geekhalo.feed.domain.feed.disable.FeedDisabledEvent;
import com.geekhalo.feed.domain.feed.enable.EnableFeedContext;
import com.geekhalo.feed.domain.feed.enable.FeedEnabledEvent;
import com.geekhalo.lego.core.command.support.AbstractAggRoot;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@Setter(AccessLevel.PRIVATE)
public class Feed extends AbstractAggRoot { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private FeedOwner owner;

    @Enumerated(EnumType.STRING)
    private FeedStatus status;

    @Embedded
    private FeedData data;

    public static Feed create(CreateFeedContext context) {

        Feed feed = new Feed();
        feed.setOwner(context.getCommand().getOwner());
        feed.setData(context.getCommand().getData());
        feed.init();

        return feed;
    }

    private void init() {
        setStatus(FeedStatus.ENABLE);
        addEvent(new FeedCreatedEvent(this));
    }

    public void enable(EnableFeedContext context) {
        // 添加代码
        if (isDisable()){
            setStatus(FeedStatus.ENABLE);
            addEvent(new FeedEnabledEvent(this));
        }

    }

    public void disable(DisableFeedContext context) {
        if (isEnable()){
            setStatus(FeedStatus.DISABLE);
            addEvent(new FeedDisabledEvent(this));
        }
    }


    public FeedIndex createIndex(){
        return new FeedIndex(getId(),
                getCreateAt().getTime(),
                getOwner().getOwnerId());
    }

    public boolean isEnable() {
        return getStatus() == FeedStatus.ENABLE;
    }

    public boolean isDisable() {
        return getStatus() == FeedStatus.DISABLE;
    }
}