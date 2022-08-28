package com.geekhalo.lego.core.singlequery;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Sort<FIELD extends Enum<FIELD> & OrderField> {
    private List<Order<FIELD>> orders = new ArrayList<>();

    @Data
    @Builder
    public static class Order<FIELD extends Enum<FIELD> & OrderField>{
        private Direction direction;
        private FIELD orderField;

        public String toOrderByClause(){
            if (orderField == null){
                return null;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(orderField.getColumnName());
            if (direction != null){
                stringBuilder.append(' ')
                        .append(direction.name());
            }
            return stringBuilder.toString();
        }
    }

    public enum  Direction{
        ASC, DESC;
    }

    public String toOrderByClause(){
        if (CollectionUtils.isEmpty(orders)){
            return null;
        }
        return orders.stream()
                .map(Order::toOrderByClause)
                .filter(StringUtils::isNoneBlank)
                .collect(Collectors.joining(",", " ", " "));
    }
}
