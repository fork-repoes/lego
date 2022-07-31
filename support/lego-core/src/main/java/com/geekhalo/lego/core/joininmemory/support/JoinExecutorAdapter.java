package com.geekhalo.lego.core.joininmemory.support;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
@Builder
@Getter
public class JoinExecutorAdapter<DATA, JOIN_KEY, JOIN_DATA, RESULT> extends AbstractJoinExecutor<DATA, JOIN_KEY, JOIN_DATA, RESULT>{
    private final String name;
    private final Function<DATA, JOIN_KEY> keyGeneratorFromData;
    private final Function<List<JOIN_KEY>, List<JOIN_DATA>> dataLoeader;
    private final Function<JOIN_DATA, JOIN_KEY> keyGeneratorFromJoinData;
    private final Function<JOIN_DATA, RESULT> dataConverter;
    private final BiConsumer<DATA, RESULT> foundFunction;
    private final BiConsumer<DATA, JOIN_KEY> lostFunction;
    private final int runLevel;

    public JoinExecutorAdapter(String name,
                               Function<DATA, JOIN_KEY> keyGeneratorFromData,
                               Function<List<JOIN_KEY>, List<JOIN_DATA>> dataLoeader,
                               Function<JOIN_DATA, JOIN_KEY> keyGeneratorFromJoinData,
                               Function<JOIN_DATA, RESULT> dataConverter,
                               BiConsumer<DATA, RESULT> foundFunction,
                               BiConsumer<DATA, JOIN_KEY> lostFunction,
                               Integer runLevel) {

        Preconditions.checkArgument(keyGeneratorFromData != null);
        Preconditions.checkArgument(dataLoeader != null);
        Preconditions.checkArgument(keyGeneratorFromJoinData != null);
        Preconditions.checkArgument(dataConverter != null);
        Preconditions.checkArgument(foundFunction != null);

        this.name = name;
        this.keyGeneratorFromData = keyGeneratorFromData;
        this.dataLoeader = dataLoeader;
        this.keyGeneratorFromJoinData = keyGeneratorFromJoinData;
        this.dataConverter = dataConverter;
        this.foundFunction = foundFunction;
        if (lostFunction != null) {
            this.lostFunction = getDefaultLostFunction().andThen(lostFunction);
        }else {
            this.lostFunction = getDefaultLostFunction();
        }

        if (runLevel == null){
            this.runLevel = 0;
        }else {
            this.runLevel = runLevel.intValue();
        }
    }

    @Override
    protected JOIN_KEY createJoinKeyFromSourceData(DATA data) {
        return this.keyGeneratorFromData.apply(data);
    }

    @Override
    protected List<JOIN_DATA> getJoinDatasByJoinKeys(List<JOIN_KEY> joinKeys) {
        return this.dataLoeader.apply(joinKeys);
    }

    @Override
    protected JOIN_KEY createJoinKeyFromJoinData(JOIN_DATA joinData) {
        return this.keyGeneratorFromJoinData.apply(joinData);
    }

    @Override
    protected RESULT convertToResult(JOIN_DATA joinData) {
        return this.dataConverter.apply(joinData);
    }

    @Override
    protected void onFound(DATA data, RESULT JoinResult) {
        this.foundFunction.accept(data, JoinResult);
    }

    @Override
    protected void onNotFound(DATA data, JOIN_KEY joinKey) {
        this.lostFunction.accept(data, joinKey);
    }

    private BiConsumer<DATA, JOIN_KEY> getDefaultLostFunction() {
        return (data, joinKey) -> {
            log.warn("failed to find join data by {} for {}", joinKey, data);
        };
    }

    @Override
    public int runOnLevel() {
        return this.runLevel;
    }

    @Override
    public String toString() {
        return "JoinExecutorAdapter-for-" + name;
    }
}
