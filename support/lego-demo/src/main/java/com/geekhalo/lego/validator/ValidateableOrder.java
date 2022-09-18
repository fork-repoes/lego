package com.geekhalo.lego.validator;

import com.geekhalo.lego.core.validator.ValidateException;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by taoli on 2022/9/19.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Entity
@Table(name = "validate_order")
@Setter
public class ValidateableOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 支付金额
     */
    private Integer payPrice;

    /**
     * 售价
     */
    private Integer sellPrice;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 折扣价
     */
    private Integer discountPrice;

    /**
     * 手工改价
     */
    private Integer manualPrice;

    @PrePersist
    @PreUpdate
    void checkPrice(){
        Integer realPayPrice = sellPrice * amount - discountPrice - manualPrice;

        if (realPayPrice != payPrice){
            throw new ValidateException("order", "570", "金额计算错误");
        }
    }
}
