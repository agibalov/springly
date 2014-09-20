package me.loki2302.springly.dummy;

import me.loki2302.springly.HandlerMethodArgumentResolver;

import java.util.Map;

public class DummyParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver<Map<String, Object>, Map<String, Object>> {
    @Override
    public boolean canResolve(Map<String, Object> parameterContext, Map<String, Object> resolutionContext) {
        return true;
    }

    @Override
    public Object resolve(Map<String, Object> parameterContext, Map<String, Object> resolutionContext) {
        String paramName = (String)parameterContext.get("paramName");
        return resolutionContext.get(paramName);
    }
}
