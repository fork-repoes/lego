package com.geekhalo.tinyurl.infra.generator.db;


import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import com.geekhalo.tinyurl.infra.generator.db.gen.NumberGen;
import com.geekhalo.tinyurl.infra.generator.db.gen.NumberType;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;

public class DBBasedBatchNumberGenerator
        extends AbstractDBBasedNumberGenerator
        implements NumberGenerator {
    @Value("${tinyurl.number.generator.batchSize:500}")
    private int batchSize;

    private List<Long> tmp = Lists.newArrayList();

    @Override
    public Long nextNumber() {
        synchronized (tmp){
            if (CollectionUtils.isEmpty(tmp)){
                do {
                    try {
                        List<Long> numbers = nextNumber(batchSize);
                        tmp.addAll(numbers);
                        break;
                    }catch (ObjectOptimisticLockingFailureException e){
                    }
                }while (true);
            }
            return tmp.remove(0);
        }
    }

    private List<Long> nextNumber(int size){
        NumberGen numberGen = this.getNumberGenRepository().getByType(NumberType.TINY_URL);
        if (numberGen == null){
            numberGen = new NumberGen(NumberType.TINY_URL);
        }

        List<Long> ids = numberGen.nextNumber(size);

        this.getNumberGenRepository().save(numberGen);
        return ids;
    }
}