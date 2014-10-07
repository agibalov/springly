package me.loki2302.springly.dummy;

import me.loki2302.springly.*;

import java.util.List;
import java.util.Map;

public final class Springly {
    private Springly() {
    }

    public static RequestProcessor<DummyHandler, Map<String, Object>, Map<String, Object>> makeRequestProcessor(final Object service) {
        DummyMetadataReader dummyMetadataReader = new DummyMetadataReader();
        HandlerReader<Map<String, Object>, Map<String, Object>, Map<String, Object>, DummyHandler> dummyHandlerReader =
                new HandlerReader<Map<String, Object>, Map<String, Object>, Map<String, Object>, DummyHandler>(dummyMetadataReader);

        HandlerMethodArgumentResolverRegistry<Map<String, Object>, Map<String, Object>> handlerMethodArgumentResolverRegistry = new HandlerMethodArgumentResolverRegistry<Map<String, Object>, Map<String, Object>>();
        handlerMethodArgumentResolverRegistry.register(new DummyParamHandlerMethodArgumentResolver());

        HandlerRegistry<DummyHandler, Map<String, Object>> handlerRegistry = new HandlerRegistry<DummyHandler, Map<String, Object>>(new HandlerPredicate<DummyHandler, Map<String, Object>>() {
            @Override
            public boolean match(DummyHandler dummyHandler, Map<String, Object> request) {
                return dummyHandler.getActionName().equals(request.get("action"));
            }
        });

        List<DummyHandler> handlers = dummyHandlerReader.readClass(service.getClass());
        handlerRegistry.register(handlers);

        RequestProcessor<DummyHandler, Map<String, Object>, Map<String, Object>> requestProcessor = new RequestProcessor<DummyHandler, Map<String, Object>, Map<String, Object>>(handlerRegistry, new HandlerInstanceResolver() {
            @Override
            public Object resolveInstance(Class<?> handlerClass) {
                return service;
            }
        }, handlerMethodArgumentResolverRegistry);

        return requestProcessor;
    }
}
