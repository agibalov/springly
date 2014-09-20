package me.loki2302.springly;

import java.util.List;

public interface Handler<TParameterMetadata> {
    Class<?> getHandlerClass();
    List<TParameterMetadata> getParameters();
    Object handle(Object instance, List<Object> arguments);
}
