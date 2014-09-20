package me.loki2302.springly.dummy;

import me.loki2302.springly.Handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class DummyHandler implements Handler<Map<String, Object>> {
    private final String actionName;
    private final Method method;
    private final Class<?> handlerClass;
    private final List<Map<String, Object>> paramsMeta;

    public DummyHandler(
            String actionName,
            Method method,
            Class<?> handlerClass,
            List<Map<String, Object>> paramsMeta) {

        this.actionName = actionName;
        this.method = method;
        this.handlerClass = handlerClass;
        this.paramsMeta = paramsMeta;
    }

    public String getActionName() {
        return actionName;
    }

    @Override
    public Class<?> getHandlerClass() {
        return handlerClass;
    }

    @Override
    public List<Map<String, Object>> getParameters() {
        return paramsMeta;
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
