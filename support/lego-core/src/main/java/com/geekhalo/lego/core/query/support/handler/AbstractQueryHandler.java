package com.geekhalo.lego.core.query.support.handler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class AbstractQueryHandler<MAIN, RESULT> implements QueryHandler<RESULT>{

    @Override
    public RESULT query(Object[] query) {
        // 1. 参数验证
        validate(query);
        // 2. 执行查询
        MAIN qResult = doQuery(query);

        if (qResult == null){
            log.info("query use {} result is null",  query);
            return null;
        }

        // 3. 对查询结果进行转换
        RESULT resultResult = convert(qResult);
        if (resultResult == null){
            log.warn("convert {} result is null", qResult);
            return null;
        }
        // 4. 为结果填充数据
        fill(resultResult);

        return resultResult;
    }


    abstract MAIN doQuery( Object[] arguments);

    abstract RESULT convert(MAIN queryResult);

    abstract RESULT fill(RESULT queryResult);

    abstract void validate(Object[] arguments);
}
