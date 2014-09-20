package me.loki2302.web;

import me.loki2302.framework.MetadataReader;
import me.loki2302.web.annotations.Controller;
import me.loki2302.web.annotations.PathParam;
import me.loki2302.web.annotations.QueryParam;
import me.loki2302.web.annotations.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ControllerMetadataReader implements MetadataReader<ControllerClassMetadata, ControllerMethodMetadata, ControllerParameterMetadata> {
    @Override
    public ControllerClassMetadata readClass(Class<?> clazz) {
        // must not be abstract
        if(Modifier.isAbstract(clazz.getModifiers())) {
            return null;
        }

        // must be public
        if(!Modifier.isPublic(clazz.getModifiers())) {
            return null;
        }

        // must be annotated with @Controller
        Controller controller = clazz.getAnnotation(Controller.class);
        if(controller == null) {
            return null;
        }

        ControllerClassMetadata controllerClassContext = new ControllerClassMetadata();
        controllerClassContext.name = clazz.getName();

        // may be annotated with @RequestMapping
        RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
        if(requestMapping != null) {
            controllerClassContext.requestMapping = requestMapping.value();
        }

        return controllerClassContext;
    }

    @Override
    public ControllerMethodMetadata readMethod(ControllerClassMetadata controllerClassContext, Method method) {
        // must not be abstract
        if(Modifier.isAbstract(method.getModifiers())) {
            return null;
        }

        // must be public
        if(!Modifier.isPublic(method.getModifiers())) {
            return null;
        }

        ControllerMethodMetadata controllerMethodContext = new ControllerMethodMetadata();
        controllerMethodContext.name = method.getName();
        controllerMethodContext.method = method;

        // may be annotated with @RequestMapping
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if(requestMapping != null) {
            controllerMethodContext.requestMapping = requestMapping.value();
        }

        return controllerMethodContext;
    }

    @Override
    public ControllerParameterMetadata readParameter(
            ControllerClassMetadata controllerClassContext,
            ControllerMethodMetadata controllerMethodContext,
            Class<?> parameterClass,
            Annotation[] parameterAnnotations) {

        if(parameterClass != int.class &&
                parameterClass != Integer.class &&
                parameterClass != String.class) {

            return null;
        }

        PathParam pathParam = findAnnotation(parameterAnnotations, PathParam.class);
        QueryParam queryParam = findAnnotation(parameterAnnotations, QueryParam.class);
        if(pathParam == null && queryParam == null) {
            return null;
        }

        if(pathParam != null && queryParam != null) {
            return null;
        }

        ControllerParameterMetadata controllerParameterContext = new ControllerParameterMetadata();
        controllerParameterContext.type = parameterClass;
        controllerParameterContext.annotations = parameterAnnotations;

        if(pathParam != null) {
            controllerParameterContext.name = pathParam.value();
        } else if(queryParam != null) {
            controllerParameterContext.name = queryParam.value();
        } else {
            throw new RuntimeException();
        }

        return controllerParameterContext;
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
