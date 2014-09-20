package me.loki2302.springly.dummy;

import me.loki2302.springly.HandlerFactory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class DummyHandlerFactory implements HandlerFactory<Map<String, Object>, Map<String, Object>, Map<String, Object>, DummyHandler> {
    @Override
    public DummyHandler makeHandler(
            Map<String, Object> classMeta,
            Map<String, Object> methodMeta,
            List<Map<String, Object>> parametersMeta) {

        String actionName = (String)methodMeta.get("actionName");
        Method method = (Method)methodMeta.get("method");
        Class<?> handlerClass = (Class<?>)classMeta.get("handlerClass");

        DummyHandler dummyHandler = new DummyHandler(
                actionName,
                method,
                handlerClass,
                parametersMeta);

        return dummyHandler;
    }
}
