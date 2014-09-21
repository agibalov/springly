package me.loki2302.springly.playground.web;

import me.loki2302.springly.HandlerFactory;

import java.util.List;

public class ControllerHandlerFactory implements HandlerFactory<ControllerClassMetadata, ControllerMethodMetadata, ControllerParameterMetadata, ControllerHandler> {
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
