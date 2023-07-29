package com.geekhalo.relation.app;

import com.geekhalo.lego.annotation.singlequery.FieldEqualTo;
import com.geekhalo.lego.annotation.singlequery.FieldIn;
import com.geekhalo.lego.core.singlequery.Pageable;
import com.geekhalo.relation.domain.relation.RelationStatus;
import com.sun.istack.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class QueryRelationByOwner {
    @NotNull
    @FieldEqualTo("key.owner")
    private Long owner;

    @FieldIn(value = "status", fieldType = RelationStatus.class)
    private List<RelationStatus> statuses;

    private Pageable pageable;

}
