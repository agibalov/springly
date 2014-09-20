package me.loki2302.framework;

import me.loki2302.framework.Handler;

public interface HandlerResolver<THandler extends Handler, TResolutionContext> {
    THandler resolve(TResolutionContext resolutionContext);
}
