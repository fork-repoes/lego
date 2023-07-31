package com.geekhalo.relation.domain.group.loader;

import com.geekhalo.relation.domain.group.RelationGroup;
import com.geekhalo.relation.domain.group.RelationGroupCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = LazyLoadGroupById.BEAN_NAME)
public class RelationGroupLoaderImpl implements RelationGroupLoader{
    @Autowired
    private RelationGroupCommandRepository commandRepository;

    @Override
    public RelationGroup loadById(Long id) {
        if (id == null || id == 0){
            return RelationGroup.createDefault();
        }

        return commandRepository.findById(id)
                .orElse(null);
    }

}
