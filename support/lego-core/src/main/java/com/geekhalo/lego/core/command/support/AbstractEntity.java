package com.geekhalo.lego.core.command.support;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.Date;

@Data
public class AbstractEntity {
    @Id
    private Long id;

    @Version
    private Integer vsn;

    @Column(name = "create_time")
    private Date createAt;

    @Column(name = "update_time")
    private Date updateAt;

    @Column(name = "delete_time")
    private Date deleteAt;
}
