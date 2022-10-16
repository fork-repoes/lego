package com.geekhalo.lego.core.web.query;

import com.geekhalo.lego.core.query.QueryServicesRegistry;
import com.geekhalo.lego.core.web.support.WebMethodRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by taoli on 2022/10/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
public class QueryMethodRegistry extends WebMethodRegistry {
    @Autowired
    private QueryServicesRegistry queryServicesRegistry;

    protected List<Object> getServices(){
        return this.queryServicesRegistry.getQueryServices();
    }
}
