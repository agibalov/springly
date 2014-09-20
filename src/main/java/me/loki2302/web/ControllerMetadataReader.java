package me.loki2302.web;

import me.loki2302.springly.ClassHelper;
import me.loki2302.springly.MetadataReader;
import me.loki2302.springly.MethodHelper;
import me.loki2302.springly.ParameterHelper;
import me.loki2302.web.annotations.Controller;
import me.loki2302.web.annotations.PathParam;
import me.loki2302.web.annotations.QueryParam;
import me.loki2302.web.annotations.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ControllerMetadataReader implements MetadataReader<ControllerClassMetadata, ControllerMethodMetadata, ControllerParameterMetadata> {
    @Override
    public ControllerClassMetadata readClass(
            Class<?> clazz,
            ClassHelper classHelper) {

        if(classHelper.isAbstract()) {
            return null;
        }

        if(!classHelper.isPublic()) {
            return null;
        }

        // must be annotated with @Controller
        Controller controller = classHelper.getAnnotation(Controller.class);
        if(controller == null) {
            return null;
        }

        ControllerClassMetadata controllerClassContext = new ControllerClassMetadata();
        controllerClassContext.name = clazz.getName();

        // may be annotated with @RequestMapping
        RequestMapping requestMapping = classHelper.getAnnotation(RequestMapping.class);
        if(requestMapping != null) {
            controllerClassContext.requestMapping = requestMapping.value();
        }

        return controllerClassContext;
    }

    @Override
    public ControllerMethodMetadata readMethod(
            ControllerClassMetadata controllerClassContext,
            Method method,
            MethodHelper methodHelper) {

        if(methodHelper.isAbstract()) {
            return null;
        }

        if(!methodHelper.isPublic()) {
            return null;
        }

        ControllerMethodMetadata controllerMethodContext = new ControllerMethodMetadata();
        controllerMethodContext.name = method.getName();
        controllerMethodContext.method = method;

        // may be annotated with @RequestMapping
        RequestMapping requestMapping = methodHelper.getAnnotation(RequestMapping.class);
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
            Annotation[] parameterAnnotations,
            ParameterHelper parameterHelper) {

        if(parameterClass != int.class &&
                parameterClass != Integer.class &&
                parameterClass != String.class) {

            return null;
        }

        PathParam pathParam = parameterHelper.getAnnotation(PathParam.class);
        QueryParam queryParam = parameterHelper.getAnnotation(QueryParam.class);
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
}
