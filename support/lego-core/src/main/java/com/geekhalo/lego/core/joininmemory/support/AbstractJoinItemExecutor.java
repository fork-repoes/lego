package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.core.joininmemory.JoinItemExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * @param <SOURCE_DATA> 原始数据
 * @param <JOIN_KEY> join 使用的 key
 * @param <JOIN_DATA> join 获取的 数据
 * @param <JOIN_RESULT> 转换后的结果数据
 */
@Slf4j
abstract class AbstractJoinItemExecutor<SOURCE_DATA, JOIN_KEY, JOIN_DATA, JOIN_RESULT> implements JoinItemExecutor<SOURCE_DATA> {

    /**
     * 从原始数据中生成 JoinKey
     * @param data
     * @return
     */
    protected abstract JOIN_KEY createJoinKeyFromSourceData(SOURCE_DATA data);

    /**
     * 根据 JoinKey 批量获取 JoinData
     * @param joinKeys
     * @return
     */
    protected abstract List<JOIN_DATA> getJoinDataByJoinKeys(List<JOIN_KEY> joinKeys);

    /**
     * 从 JoinData 中获取 JoinKey
     * @param joinData
     * @return
     */
    protected abstract JOIN_KEY createJoinKeyFromJoinData(JOIN_DATA joinData);

    /**
     * 将 JoinData 转换为 JoinResult
     * @param joinData
     * @return
     */
    protected abstract JOIN_RESULT convertToResult(JOIN_DATA joinData);

    /**
     * 将 JoinResult 写回至 SourceData
     * @param data
     * @param JoinResult
     */
    protected abstract void onFound(SOURCE_DATA data, JOIN_RESULT JoinResult);

    /**
     * 未找到对应的 JoinData
     * @param data
     * @param joinKey
     */
    protected abstract void onNotFound(SOURCE_DATA data, JOIN_KEY joinKey);

    @Override
    public void execute(List<SOURCE_DATA> sourceDatas) {
        // 从源数据中提取 JoinKey
        List<JOIN_KEY> joinKeys = sourceDatas.stream()
                .filter(Objects::nonNull)
                .map(this::createJoinKeyFromSourceData)
                .filter(Objects::nonNull)
                .distinct()
                .collect(toList());
        log.debug("get join key {} from source data {}", joinKeys, sourceDatas);

        // 根据 JoinKey 获取 JoinData
        List<JOIN_DATA> joinDatas = getJoinDataByJoinKeys(joinKeys);
        log.debug("get join data {} by join key {}", joinDatas, joinKeys);

        // 将 JoinData 以 Map 形式进行组织
        Map<JOIN_KEY, JOIN_DATA> joinDataMap = joinDatas.stream()
                .filter(Objects::nonNull)
                .collect(toMap(this::createJoinKeyFromJoinData, Function.identity(), (a, b) -> a));
        log.debug("group by join key, result is {}", joinDataMap);

        // 处理每一条 SourceData
        for (SOURCE_DATA data : sourceDatas){
            // 从 SourceData 中 获取 JoinKey
            JOIN_KEY joinKey = createJoinKeyFromSourceData(data);
            if (joinKey == null){
                log.warn("join key from join data {} is null", data);
                continue;
            }
            // 根据 JoinKey 获取 JoinData
            JOIN_DATA joinData = joinDataMap.get(joinKey);
            if (joinData != null){
                // 获取到 JoinData， 转换为 JoinResult，进行数据写回
                JOIN_RESULT joinResult = convertToResult(joinData);
                log.debug("success to convert join data {} to join result {}", joinData, joinResult);
                onFound(data, joinResult);
                log.debug("success to write join result {} to source data {}", joinResult, data);
            }else {
                log.warn("join data lost by join key {} for source data {}", joinData, data);
                // 为获取到 JoinData，进行 notFound 回调
                onNotFound(data, joinKey);
            }
        }
    }
}
