package com.geekhalo.lego.loader;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreateOrderCmd {
    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private int count;
}
