package me.loki2302.springly.playground.web;

import me.loki2302.springly.ClassHelper;
import me.loki2302.springly.MetadataReader;
import me.loki2302.springly.MethodHelper;
import me.loki2302.springly.ParameterHelper;
import me.loki2302.springly.playground.web.annotations.Controller;
import me.loki2302.springly.playground.web.annotations.PathParam;
import me.loki2302.springly.playground.web.annotations.QueryParam;
import me.loki2302.springly.playground.web.annotations.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class ControllerMetadataReader implements MetadataReader<ControllerClassMetadata, ControllerMethodMetadata, ControllerParameterMetadata, ControllerHandler> {
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

        ControllerClassMetadata classMetadata = new ControllerClassMetadata();
        classMetadata.clazz = clazz;
        classMetadata.name = clazz.getName();

        // may be annotated with @RequestMapping
        RequestMapping requestMapping = classHelper.getAnnotation(RequestMapping.class);
        if(requestMapping != null) {
            classMetadata.requestMapping = requestMapping.value();
        }

        return classMetadata;
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

        ControllerMethodMetadata methodMetadata = new ControllerMethodMetadata();
        methodMetadata.name = method.getName();
        methodMetadata.method = method;

        // may be annotated with @RequestMapping
        RequestMapping requestMapping = methodHelper.getAnnotation(RequestMapping.class);
        if(requestMapping != null) {
            methodMetadata.requestMapping = requestMapping.value();
        }

        return methodMetadata;
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

        ControllerParameterMetadata parameterMetadata = new ControllerParameterMetadata();
        parameterMetadata.type = parameterClass;
        parameterMetadata.annotations = parameterAnnotations;

        if(pathParam != null) {
            parameterMetadata.name = pathParam.value();
        } else if(queryParam != null) {
            parameterMetadata.name = queryParam.value();
        } else {
            throw new RuntimeException();
        }

        return parameterMetadata;
    }

    @Override
    public ControllerHandler makeHandler(
            ControllerClassMetadata classMetadata,
            ControllerMethodMetadata methodMetadata,
            List<ControllerParameterMetadata> parametersMetadata) {

        ControllerHandler controllerHandler = new ControllerHandler();
        controllerHandler.handlerClass = classMetadata.clazz;
        controllerHandler.name = classMetadata.name + methodMetadata.name;
        controllerHandler.requestMapping = classMetadata.requestMapping + "/" + methodMetadata.requestMapping;
        controllerHandler.method = methodMetadata.method;
        controllerHandler.parameters = parametersMetadata;

        return controllerHandler;
    }
}
