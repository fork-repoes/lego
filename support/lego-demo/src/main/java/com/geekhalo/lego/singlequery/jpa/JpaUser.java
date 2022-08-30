package com.geekhalo.lego.singlequery.jpa;

import com.geekhalo.lego.singlequery.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
@Entity
@Table(name = "t_user")
public class JpaUser implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer status;

    private Date birthAt;

    private String mobile;
}
