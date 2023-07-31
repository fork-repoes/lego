package com.geekhalo.relation.domain.group.loader;

import com.geekhalo.relation.domain.group.RelationGroup;

public interface RelationGroupLoader {
    RelationGroup loadById(Long id);
}
