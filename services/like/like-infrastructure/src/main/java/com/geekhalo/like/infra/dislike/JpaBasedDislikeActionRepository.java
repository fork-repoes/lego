package com.geekhalo.like.infra.dislike;

import com.geekhalo.like.domain.ActionStatus;
import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;
import com.geekhalo.like.domain.user.ActionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface JpaBasedDislikeActionRepository
        extends DislikeActionRepository, JpaRepository<DislikeAction, Long> {


    @Override
    default DislikeAction sync(DislikeAction dislikeAction){
        return this.save(dislikeAction);
//        if (dislikeAction.getId() == null && dislikeAction.getVsn() == null){
//            return save(dislikeAction);
//        }else {
//            updateByIdAndUserId(dislikeAction.getStatus(), dislikeAction.getUpdateAt(), dislikeAction.getId(), dislikeAction.getVsn(), dislikeAction.getUser());
//            return dislikeAction;
//        }
    }

//    @Modifying
//    @Query("update DislikeAction a " +
//            "set " +
//            "   a.status = :status," +
//            "   a.updateAt =:updateDate " +
//            "where " +
//            "   a.id = :id and a.vsn = :vsn and a.user = :user")
//    void updateByIdAndUserId(@Param("status") ActionStatus status,
//                             @Param("updateDate") Date updateDate,
//                             @Param("id") Long id,
//                             @Param("vsn") Integer vsn,
//                             @Param("user") ActionUser user);


}
