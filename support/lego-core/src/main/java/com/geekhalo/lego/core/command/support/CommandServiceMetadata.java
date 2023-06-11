package com.geekhalo.lego.core.command.support;

import com.geekhalo.lego.core.command.CommandServiceDefinition;
import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Value;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
@Builder
public class CommandServiceMetadata {
    private Class domainClass;

    private Class repositoryClass;

    private Class commandServiceClass;

    public static CommandServiceMetadata fromCommandService(Class commandService){
        CommandServiceDefinition mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(commandService, CommandServiceDefinition.class);

        Preconditions.checkArgument(mergedAnnotation != null);

        return CommandServiceMetadata.builder()
                .domainClass(mergedAnnotation.domainClass())
                .repositoryClass(mergedAnnotation.repositoryClass())
                .commandServiceClass(commandService)
                .build();
    }
}
