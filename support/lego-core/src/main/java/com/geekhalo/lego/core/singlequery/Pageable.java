package com.geekhalo.lego.core.singlequery;

import lombok.Data;

/**
 * Created by taoli on 2022/7/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 分页参数
 */
@Data
public class Pageable {
    /**
     * 页号
     */
    private Integer pageNo;
    /**
     * 每页大小
     */
    private Integer pageSize;

    public Integer getLimit(){
        return pageSize;
    }

    /**
     * 偏移起始量，从 0 开始
     * @return
     */
    public Integer getOffset(){
        if (pageNo == null || pageSize == null){
            return null;
        }
        return pageNo * pageSize;
    }

}
