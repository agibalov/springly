package me.loki2302.springly;

public interface HandlerResolver<THandler extends Handler, TResolutionContext> {
    THandler resolve(TResolutionContext resolutionContext);
}
