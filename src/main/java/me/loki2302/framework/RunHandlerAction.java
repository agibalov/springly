package me.loki2302.framework;

import me.loki2302.framework.Handler;

import java.util.List;

public class RunHandlerAction<THandler extends Handler> {
    private final THandler handler;
    private final List<Object> arguments;

    public RunHandlerAction(THandler handler, List<Object> arguments) {
        this.handler = handler;
        this.arguments = arguments;
    }

    public Object run(Object instance) {
        return handler.handle(instance, arguments);
    }
}
