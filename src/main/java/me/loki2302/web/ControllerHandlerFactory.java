package me.loki2302.web;

import me.loki2302.framework.HandlerFactory;

import java.util.List;

public class ControllerHandlerFactory implements HandlerFactory<ControllerClassMetadata, ControllerMethodMetadata, ControllerParameterMetadata, ControllerHandler> {
    @Override
    public ControllerHandler makeHandler(
            ControllerClassMetadata classMetadata,
            ControllerMethodMetadata methodMetadata,
            List<ControllerParameterMetadata> parametersMetadata) {

        ControllerHandler controllerHandler = new ControllerHandler();
        controllerHandler.name = classMetadata.name + methodMetadata.name;
        controllerHandler.requestMapping = classMetadata.requestMapping + "/" + methodMetadata.requestMapping;
        controllerHandler.method = methodMetadata.method;
        controllerHandler.parameters = parametersMetadata;

        return controllerHandler;
    }
}
