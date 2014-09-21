package me.loki2302.springly.playground.web;

import me.loki2302.springly.Handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ControllerHandler implements Handler<ControllerParameterMetadata> {
    public Class<?> handlerClass;
    public String name;
    public String requestMapping;
    public Method method;
    public List<ControllerParameterMetadata> parameters;

    @Override
    public Class<?> getHandlerClass() {
        return handlerClass;
    }

    @Override
    public List<ControllerParameterMetadata> getParameters() {
        return parameters;
    }

    @Override
    public Object handle(Object instance, List<Object> arguments) {
        try {
            return method.invoke(instance, arguments.toArray());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
