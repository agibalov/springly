package me.loki2302.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HandlerRegistry<THandler extends Handler, TResolutionContext> implements HandlerResolver<THandler, TResolutionContext> {
    private final List<THandler> handlers = new ArrayList<THandler>();
    private final HandlerPredicate<THandler, TResolutionContext> handlerPredicate;

    public HandlerRegistry(HandlerPredicate<THandler, TResolutionContext> handlerPredicate) {
        this.handlerPredicate = handlerPredicate;
    }

    public void register(THandler handler) {
        handlers.add(handler);
    }

    public void register(Collection<THandler> handlers) {
        this.handlers.addAll(handlers);
    }

    public THandler resolve(TResolutionContext resolutionContext) {
        for(THandler handler : handlers) {
            if(handlerPredicate.match(handler, resolutionContext)) {
                return handler;
            }
        }

        return null;
    }
}
