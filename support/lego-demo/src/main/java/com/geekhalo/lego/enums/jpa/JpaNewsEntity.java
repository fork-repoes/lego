package com.geekhalo.lego.enums.jpa;

import com.geekhalo.lego.enums.NewsStatus;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by taoli on 2022/12/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Entity
@Data
@Table(name = "t_jpa_news")
public class JpaNewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = JpaNewsStatusConverter.class)
    private NewsStatus status;
}
