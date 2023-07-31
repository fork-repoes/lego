package com.geekhalo.relation.app.group;

import com.geekhalo.lego.annotation.singlequery.FieldEqualTo;
import com.geekhalo.lego.annotation.singlequery.FieldIn;
import com.geekhalo.relation.domain.group.RelationGroupStatus;
import com.sun.istack.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class QueryGroupByOwner {
    @NotNull
    @FieldEqualTo("owner")
    private Long owner;

    @FieldIn(value = "status", fieldType = RelationGroupStatus.class)
    private List<RelationGroupStatus> statuses;

}
