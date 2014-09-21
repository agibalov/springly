package me.loki2302.springly.playground.web;

import me.loki2302.springly.HandlerMethodArgumentResolver;
import me.loki2302.springly.playground.web.annotations.PathParam;

import java.lang.annotation.Annotation;

public class PathParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver<ControllerParameterMetadata, ControllerRequest> {
    @Override
    public boolean canResolve(ControllerParameterMetadata controllerParameterContext, ControllerRequest resolutionContext) {
        PathParam pathParam = findAnnotation(controllerParameterContext.annotations, PathParam.class);
        if(pathParam == null) {
            return false;
        }

        return true;
    }

    @Override
    public Object resolve(ControllerParameterMetadata controllerParameterContext, ControllerRequest resolutionContext) {
        PathParam pathParam = findAnnotation(controllerParameterContext.annotations, PathParam.class);
        String name = pathParam.value();
        return resolutionContext.pathParams.get(name);
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
