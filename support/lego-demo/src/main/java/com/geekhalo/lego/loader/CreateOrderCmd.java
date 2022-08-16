package com.geekhalo.lego.loader;

import lombok.Data;

@Data
public class CreateOrderCmd {
    private Long userId;

    private Long productId;

    private int count;
}
