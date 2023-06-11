package com.geekhalo.lego.core.command.support.handler;

import com.geekhalo.lego.core.command.AggNotFoundException;
import com.geekhalo.lego.core.command.AggRoot;
import com.geekhalo.lego.core.command.support.handler.aggloader.AggLoader;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import com.google.common.base.Preconditions;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.function.Consumer;

@Setter
public class UpdateAggCommandHandler<
        AGG extends AggRoot,
        CMD,
        CONTEXT,
        RESULT>
        extends AbstractAggCommandHandler<AGG, CMD, CONTEXT, RESULT>{

    private AggLoader<CMD, AGG> aggLoader;

    // 聚合丢失处理器，聚合丢失时进行回调
    private Consumer<CONTEXT> onNotExistFun = context ->{
        throw new AggNotFoundException(context);
    };

    public UpdateAggCommandHandler(ValidateService validateService,
                                   LazyLoadProxyFactory lazyLoadProxyFactory,
                                   ApplicationEventPublisher eventPublisher,
                                   TransactionTemplate transactionTemplate) {
        super(validateService, lazyLoadProxyFactory, eventPublisher, transactionTemplate);
    }

    @Override
    protected AGG getOrCreateAgg(CMD cmd, CONTEXT context) {
        Optional<AGG> aggOptional = aggLoader.load(cmd);
        if (aggOptional.isPresent()){
            return aggOptional.get();
        }else {
            this.onNotExistFun.accept(context);
            return null;
        }
    }

    @Override
    public void validate(){
        super.validate();
        Preconditions.checkArgument(this.aggLoader != null, "Agg Loader Can not be null");
    }

    public void setAggLoader(AggLoader<CMD, AGG> aggLoader) {
        Preconditions.checkArgument(aggLoader != null);
        this.aggLoader = aggLoader;
    }

    public void addOnNotFound(Consumer<CONTEXT>  onNotExistFun){
        Preconditions.checkArgument(onNotExistFun != null);
        this.onNotExistFun = onNotExistFun.andThen(this.onNotExistFun);
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\tUpdateCommand:").append("\n")
                .append("\t\t").append("ContextFactory:").append("\t").append(this.getContextFactory()).append("\n")
                .append("\t\t").append("AggLoader:").append("\t").append(this.aggLoader).append("\n")
                .append("\t\t").append("BizMethods:").append("\t").append(this.getBizMethods()).append("\n")
                .append("\t\t").append("AggSyncer:").append("\t").append(this.getAggSyncer()).append("\n")
                .append("\t\t").append("ResultConverter").append("\t").append(this.getResultConverter()).append("\n");
        return stringBuilder.toString();
    }
}
