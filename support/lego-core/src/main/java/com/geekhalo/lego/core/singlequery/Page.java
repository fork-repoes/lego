package com.geekhalo.lego.core.singlequery;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * Created by taoli on 2022/8/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class Page<T> {
    private final List<T> content;
    private final Pageable pageable;

    private final long totalElements;

    public Page(List<T> content, Pageable pageable, long totalElements) {
        this.content = content;
        this.pageable = pageable;
        this.totalElements = totalElements;
    }

    public int getCurrentPage(){
        return this.pageable.getPageNo();
    }

    public int getPageSize(){
        return pageable.getPageSize();
    }

    public List<T> getContent(){
        return this.content;
    }

    public int getTotalPages(){
        return getPageSize() == 0 ? 1 : (int) Math.ceil((double) totalElements / (double) getPageSize());
    }

    public long getTotalElements(){
        return this.totalElements;
    }

    public boolean hasContent(){
        return CollectionUtils.isNotEmpty(this.content);
    }

    public boolean isFirst(){
        return !hasPrevious();
    }

    public boolean isLast(){
        return !hasNext();
    }

    public boolean hasNext(){
        return getCurrentPage() + 1 < getTotalPages();
    }

    public boolean hasPrevious(){
        return getCurrentPage() > 0;
    }
}
