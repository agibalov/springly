package me.loki2302.springly.playground.web;

import me.loki2302.springly.HandlerPredicate;

public class ControllerHandlerPredicate implements HandlerPredicate<ControllerHandler, ControllerRequest> {
    @Override
    public boolean match(ControllerHandler controllerHandler, ControllerRequest controllerRequest) {
        String handlerRequestMapping = controllerHandler.requestMapping;
        return handlerRequestMapping.equals(controllerRequest.path);
    }
}
