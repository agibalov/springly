package me.loki2302.framework;

import java.util.List;

public interface HandlerFactory<TClassContext, TMethodContext, TParameterContext, THandler extends Handler> {
    THandler makeHandler(
            TClassContext classContext,
            TMethodContext methodContext,
            List<TParameterContext> parameterContexts);
}
