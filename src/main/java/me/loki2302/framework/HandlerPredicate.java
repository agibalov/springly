package me.loki2302.framework;

public interface HandlerPredicate<THandler, TResolutionContext> {
    boolean match(THandler handler, TResolutionContext resolutionContext);
}
