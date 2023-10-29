package com.geekhalo.lego.command;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

public class OrderListener {
    @PrePersist
    public void preCreate(Order order) {
        order.checkBiz();
    }

    @PostPersist
    public void postCreate(Order order) {
        order.checkBiz();
    }
}
