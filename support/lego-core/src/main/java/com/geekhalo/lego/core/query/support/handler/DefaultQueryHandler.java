package com.geekhalo.lego.core.query.support.handler;

import com.geekhalo.lego.core.query.support.handler.converter.ResultConverter;
import com.geekhalo.lego.core.query.support.handler.executor.QueryExecutor;
import com.geekhalo.lego.core.query.support.handler.filler.ResultFiller;
import com.geekhalo.lego.core.validator.ValidateService;
import com.google.common.base.Preconditions;

import java.util.Arrays;

public class DefaultQueryHandler<MAIN, RESULT> extends AbstractQueryHandler<MAIN, RESULT>{
    private final ValidateService validateService;
    private final QueryExecutor<MAIN> queryExecutor;
    private final ResultConverter<MAIN, RESULT> resultConverter;
    private final ResultFiller<RESULT> resultResultFiller;

    public DefaultQueryHandler(ValidateService validateService,
                               QueryExecutor<MAIN> queryExecutor,
                               ResultConverter<MAIN, RESULT> resultConverter,
                               ResultFiller<RESULT> resultResultFiller) {
        Preconditions.checkArgument(validateService != null);
        Preconditions.checkArgument(queryExecutor != null);
        Preconditions.checkArgument(resultConverter != null);
        Preconditions.checkArgument(resultResultFiller != null);

        this.validateService = validateService;
        this.queryExecutor = queryExecutor;
        this.resultConverter = resultConverter;
        this.resultResultFiller = resultResultFiller;
    }

    @Override
    MAIN doQuery(Object[] arguments) {
        return this.queryExecutor.query(arguments);
    }

    @Override
    RESULT convert(MAIN queryResult) {
        return this.resultConverter.converter(queryResult);
    }

    @Override
    RESULT fill(RESULT queryResult) {
        return this.resultResultFiller.fill(queryResult);
    }

    @Override
    void validate(Object[] arguments) {
        this.validateService.validateParam(Arrays.asList(arguments));
    }
}
