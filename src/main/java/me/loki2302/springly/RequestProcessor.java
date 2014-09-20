package me.loki2302.springly;

import java.util.ArrayList;
import java.util.List;

public class RequestProcessor<THandler extends Handler, TParameterMetadata, TRequest> {
    private final HandlerResolver<THandler, TRequest> handlerResolver;
    private final HandlerInstanceResolver handlerInstanceResolver;
    private final HandlerMethodArgumentResolverRegistry<TParameterMetadata, TRequest> handlerMethodArgumentResolverRegistry;

    public RequestProcessor(
            HandlerResolver<THandler, TRequest> handlerResolver,
            HandlerInstanceResolver handlerInstanceResolver,
            HandlerMethodArgumentResolverRegistry<TParameterMetadata, TRequest> handlerMethodArgumentResolverRegistry) {

        this.handlerResolver = handlerResolver;
        this.handlerInstanceResolver = handlerInstanceResolver;
        this.handlerMethodArgumentResolverRegistry = handlerMethodArgumentResolverRegistry;
    }

    public Object processRequest(TRequest request) {
        THandler handler = handlerResolver.resolve(request);
        if(handler == null) {
            return null;
        }

        List<Object> arguments = resolveArguments(handler, request);

        Class<?> handlerClass = handler.getHandlerClass();
        Object instance = handlerInstanceResolver.resolveInstance(handlerClass);
        Object result = handler.handle(instance, arguments);
        return result;
    }

    private List<Object> resolveArguments(THandler handler, TRequest request) {
        List<Object> arguments = new ArrayList<Object>();
        List<TParameterMetadata> parametersMetadata = handler.getParameters();
        for(TParameterMetadata parameterMetadata : parametersMetadata) {
            Object argument = handlerMethodArgumentResolverRegistry.resolve(parameterMetadata, request);
            arguments.add(argument);
        }
        return arguments;
    }
}
