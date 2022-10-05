package com.geekhalo.lego.core.command;

/**
 * Created by taoli on 2022/10/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class NullContextForCreate<C extends CommandForCreate> implements ContextForCreate<C>{
    private final C command;

    private NullContextForCreate(C command) {
        this.command = command;
    }

    @Override
    public C getCommand() {
        return this.command;
    }

    public static <C extends CommandForCreate> NullContextForCreate apply(C command){
        return new NullContextForCreate(command);
    }
}
