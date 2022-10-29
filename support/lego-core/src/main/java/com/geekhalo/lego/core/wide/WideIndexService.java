package com.geekhalo.lego.core.wide;

import java.util.List;

/**
 * Created by taoli on 2022/10/27.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface WideIndexService<MASTER_DATA_ID,
        MASTER_DATA extends WideMasterData,
        WIDE extends Wide,
        ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemTypes<ITEM_TYPE>> {

    void index(MASTER_DATA_ID id);

    void index(List<MASTER_DATA_ID> ids);

    void indexMasterData(MASTER_DATA data);

    void indexMasterData(List<MASTER_DATA> datas);


    <KEY> void updateItem(ITEM_TYPE type, KEY key);
}
