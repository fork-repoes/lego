package com.geekhalo.lego.core.web.support;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.geekhalo.lego.core.web.RestResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import springfox.documentation.RequestHandler;
import springfox.documentation.RequestHandlerKey;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spring.wrapper.NameValueExpression;
import springfox.documentation.spring.wrapper.PatternsRequestCondition;
import springfox.documentation.spring.wrapper.RequestMappingInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
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
public class RestRequestParamRequestHandler implements RequestHandler {
    private static final TypeResolver TYPE_RESOLVER = new TypeResolver();
    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
    private final String serviceName;
    private final String serviceType;
    private final String methodName;
    private final String basePath;
    private final MultiParamMethod multiParamMethod;
    private final Set<RequestMethod> supportedMethods;

    public RestRequestParamRequestHandler(String serviceName,
                                          String serviceType,
                                          String methodName,
                                          String basePath,
                                          MultiParamMethod multiParamMethod,
                                          Set<RequestMethod> supportedMethods) {
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.methodName = methodName;
        this.basePath = basePath;
        this.multiParamMethod = multiParamMethod;
        this.supportedMethods = supportedMethods;
    }

    @Override
    public Class<?> declaringClass() {
        return multiParamMethod.getMethod().getDeclaringClass();
    }

    @Override
    public boolean isAnnotatedWith(Class<? extends Annotation> annotation) {
        return findAnnotation(annotation).isPresent();
    }

    @Override
    public PatternsRequestCondition getPatternsCondition() {
        String url = String.format("%s/%s/%s", this.basePath, serviceName, methodName);
        return new FixPatternsRequestCondition(url);
    }

    @Override
    public String groupName() {
        return String.format("%s-%s", this.serviceName, this.serviceType);
    }

    @Override
    public String getName() {
        return this.methodName;
    }

    @Override
    public Set<RequestMethod> supportedMethods() {
        return this.supportedMethods;
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
        Type[] parameterTypes = this.multiParamMethod.getMethod().getGenericParameterTypes();
        String[] parameterNames = PARAMETER_NAME_DISCOVERER.getParameterNames(this.multiParamMethod.getMethod());
        List<ResolvedMethodParameter> result = Lists.newArrayListWithCapacity(parameterTypes.length);
        int index = 0;
        for (Type paramType: parameterTypes){
            String paramName = parameterNames[index];
            List<Annotation> annotations = Lists.newArrayList();
            if (((Class) paramType).isPrimitive()) {
                RequestParam requestParam = new RequestParam() {
                    @Override
                    public Class<? extends Annotation> annotationType() {
                        return RequestParam.class;
                    }

                    @Override
                    public String value() {
                        return "";
                    }

                    @Override
                    public String name() {
                        return paramName;
                    }

                    @Override
                    public boolean required() {
                        return false;
                    }

                    @Override
                    public String defaultValue() {
                        return "";
                    }
                };
                annotations.add(requestParam);
            }

            ResolvedMethodParameter methodParameter = new ResolvedMethodParameter(
                    index ++,
                    paramName,
                    annotations,
                    TYPE_RESOLVER.resolve(paramType));
            result.add(methodParameter);
        }
        return result;
    }

    @Override
    public ResolvedType getReturnType() {
        Type genericReturnType = this.multiParamMethod.getMethod().getGenericReturnType();
        genericReturnType = genericReturnType == Void.TYPE ? Void.class : genericReturnType;
        return TYPE_RESOLVER.resolve(RestResult.class, genericReturnType);
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
        return new HandlerMethod(this.multiParamMethod.getBean(), this.multiParamMethod.getMethod());
    }

    @Override
    public RequestHandler combine(RequestHandler other) {
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
