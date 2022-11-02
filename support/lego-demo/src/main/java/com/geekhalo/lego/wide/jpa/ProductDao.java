package com.geekhalo.lego.wide.jpa;

import com.geekhalo.lego.service.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface ProductDao extends JpaRepository<Product, Long> {
}
