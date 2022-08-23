package com.geekhalo.lego.core.singlequery;

import lombok.Data;

@Data
public class Pageable {
    private Integer pageNo;

    private Integer pageSize;


    public Integer getLimit(){
        return pageSize;
    }

    public Integer getOffset(){
        if (pageNo == null || pageSize == null){
            return null;
        }
        return (pageNo - 1) * pageSize;
    }

}
