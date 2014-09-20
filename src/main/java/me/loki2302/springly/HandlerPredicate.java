package me.loki2302.springly;

public interface HandlerPredicate<THandler, TResolutionContext> {
    boolean match(THandler handler, TResolutionContext resolutionContext);
}
