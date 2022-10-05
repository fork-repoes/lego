package com.geekhalo.lego.core.command;

/**
 * Created by taoli on 2022/10/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class NullContextForUpdate<ID, C extends CommandForUpdate<ID>>
        implements ContextForUpdate<ID, C>{
    private final C command;

    public NullContextForUpdate(C command) {
        this.command = command;
    }

    @Override
    public C getCommand() {
        return command;
    }

    public static <ID, C extends CommandForUpdate> NullContextForUpdate apply(C command){
        return new NullContextForUpdate(command);
    }
}
