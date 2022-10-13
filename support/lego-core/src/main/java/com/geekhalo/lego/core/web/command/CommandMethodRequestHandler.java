package com.geekhalo.lego.core.web.command;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.geekhalo.lego.core.web.RestResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import springfox.documentation.RequestHandler;
import springfox.documentation.RequestHandlerKey;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spring.wrapper.NameValueExpression;
import springfox.documentation.spring.wrapper.PatternsRequestCondition;
import springfox.documentation.spring.wrapper.RequestMappingInfo;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

/**
 * Created by taoli on 2022/10/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class CommandMethodRequestHandler implements RequestHandler {
    private static final TypeResolver TYPE_RESOLVER = new TypeResolver();
    private final String serviceName;
    private final String methodName;
    private final CommandMethod commandMethod;

    public CommandMethodRequestHandler(String serviceName, String methodName, CommandMethod commandMethod) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.commandMethod = commandMethod;
    }

    @Override
    public Class<?> declaringClass() {
        return commandMethod.getMethod().getDeclaringClass();
    }

    @Override
    public boolean isAnnotatedWith(Class<? extends Annotation> annotation) {
        return findAnnotation(annotation).isPresent();
    }

    @Override
    public PatternsRequestCondition getPatternsCondition() {
        String url = String.format("command/%s/%s", serviceName, methodName);
        return new FixPatternsRequestCondition(url);
    }

    @Override
    public String groupName() {
        return this.serviceName;
    }

    @Override
    public String getName() {
        return this.methodName;
    }

    @Override
    public Set<RequestMethod> supportedMethods() {
        return Sets.newHashSet(RequestMethod.POST);
    }

    @Override
    public Set<MediaType> produces() {
        return Sets.newHashSet(MediaType.APPLICATION_JSON);
    }

    @Override
    public Set<MediaType> consumes() {
        return Sets.newHashSet(MediaType.APPLICATION_JSON);
    }

    @Override
    public Set<NameValueExpression<String>> headers() {
        return Sets.newHashSet();
    }

    @Override
    public Set<NameValueExpression<String>> params() {
        return Sets.newHashSet();
    }

    @Override
    public <T extends Annotation> Optional<T> findAnnotation(Class<T> annotation) {
        if (getHandlerMethod() != null) {
            return ofNullable(AnnotationUtils.findAnnotation(getHandlerMethod().getMethod(), annotation));
        }
        return empty();
    }

    @Override
    public RequestHandlerKey key() {
        return new RequestHandlerKey(
                getPatternsCondition().getPatterns(),
                supportedMethods(),
                consumes(),
                produces());
    }

    @Override
    public List<ResolvedMethodParameter> getParameters() {
        List<Annotation> annotations = Lists.newArrayList();
        RequestBody requestBody = new RequestBody() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return RequestBody.class;
            }

            @Override
            public boolean required() {
                return true;
            }
        };
        annotations.add(requestBody);
        ResolvedMethodParameter methodParameter = new ResolvedMethodParameter(
                0,
                "data",
                annotations,
                TYPE_RESOLVER.resolve(this.commandMethod.getParamCls()));
        return Lists.newArrayList(methodParameter);
    }

    @Override
    public ResolvedType getReturnType() {
        Class returnCls = this.commandMethod.getReturnCls();
        returnCls = returnCls == Void.TYPE ? Void.class : returnCls;
        return TYPE_RESOLVER.resolve(RestResult.class, returnCls);
    }

    @Override
    public <T extends Annotation> Optional<T> findControllerAnnotation(Class<T> annotation) {
        return Optional.empty();
    }

    @Override
    public RequestMappingInfo<?> getRequestMapping() {
        return null;
    }

    @Override
    public HandlerMethod getHandlerMethod() {
        return new HandlerMethod(this.commandMethod.getBean(), this.commandMethod.getMethod());
    }

    @Override
    public RequestHandler combine(RequestHandler other) {
//        return new CombinedRequestHandler(this, other);
        return other;
    }

    class FixPatternsRequestCondition implements PatternsRequestCondition{
        private final String url;

        FixPatternsRequestCondition(String url) {
            this.url = url;
        }

        @Override
        public PatternsRequestCondition combine(PatternsRequestCondition other) {
            if (other instanceof FixPatternsRequestCondition){
                String oUrl =  ((FixPatternsRequestCondition)other).url;
                return new FixPatternsRequestCondition(oUrl + "/" + this.url);
            }
            return other;
        }

        @Override
        public Set<String> getPatterns() {
            return Sets.newHashSet(url);
        }
    }
}
