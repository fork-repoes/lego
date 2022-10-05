package com.geekhalo.lego.command;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
@Entity
@Table(name = "command_order_pay_record")
@Setter(AccessLevel.PRIVATE)
public class PayRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String channel;

    @Column(name = "paid_time")
    private Date paidTime;


    private Long price;

    public static PayRecord create(String chanel, Long price) {
        PayRecord payRecord = new PayRecord();
        payRecord.setChannel(chanel);
        payRecord.setPrice(price);
        payRecord.setPaidTime(new Date());
        return payRecord;
    }
}
