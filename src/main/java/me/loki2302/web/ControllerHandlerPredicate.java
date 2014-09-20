package me.loki2302.web;

import me.loki2302.framework.HandlerPredicate;
import me.loki2302.web.ControllerHandler;
import me.loki2302.web.ControllerRequest;

public class ControllerHandlerPredicate implements HandlerPredicate<ControllerHandler, ControllerRequest> {
    @Override
    public boolean match(ControllerHandler controllerHandler, ControllerRequest controllerRequest) {
        String handlerRequestMapping = controllerHandler.requestMapping;
        return handlerRequestMapping.equals(controllerRequest.path);
    }
}
