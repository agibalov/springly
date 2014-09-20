package me.loki2302.framework;

import java.util.ArrayList;
import java.util.List;

public class RequestProcessor<THandler extends Handler, TParameterMetadata, TRequest> {
    private final HandlerResolver<THandler, TRequest> handlerResolver;
    private final HandlerMethodArgumentResolverRegistry<TParameterMetadata, TRequest> handlerMethodArgumentResolverRegistry;

    public RequestProcessor(
            HandlerResolver<THandler, TRequest> handlerResolver,
            HandlerMethodArgumentResolverRegistry<TParameterMetadata, TRequest> handlerMethodArgumentResolverRegistry) {

        this.handlerResolver = handlerResolver;
        this.handlerMethodArgumentResolverRegistry = handlerMethodArgumentResolverRegistry;
    }

    public RunHandlerAction<THandler> processRequest(TRequest request) {
        THandler handler = handlerResolver.resolve(request);
        if(handler == null) {
            return null;
        }

        List<Object> arguments = resolveArguments(handler, request);

        return new RunHandlerAction<THandler>(handler, arguments);
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
