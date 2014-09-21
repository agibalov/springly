package me.loki2302.springly.playground.web;

import me.loki2302.springly.HandlerMethodArgumentResolver;
import me.loki2302.springly.playground.web.annotations.QueryParam;

import java.lang.annotation.Annotation;

public class QueryParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver<ControllerParameterMetadata, ControllerRequest> {
    @Override
    public boolean canResolve(ControllerParameterMetadata controllerParameterContext, ControllerRequest resolutionContext) {
        QueryParam queryParam = findAnnotation(controllerParameterContext.annotations, QueryParam.class);
        if(queryParam == null) {
            return false;
        }

        return true;
    }

    @Override
    public Object resolve(ControllerParameterMetadata controllerParameterContext, ControllerRequest resolutionContext) {
        QueryParam queryParam = findAnnotation(controllerParameterContext.annotations, QueryParam.class);
        String name = queryParam.value();
        return resolutionContext.queryParams.get(name);
    }

    private static <TAnnotation extends Annotation> TAnnotation findAnnotation(Annotation[] annotations, Class<? extends Annotation> annotationClass) {
        for(Annotation annotation : annotations) {
            if(annotation.annotationType() == annotationClass) {
                return (TAnnotation)annotation;
            }
        }

        return null;
    }
}
