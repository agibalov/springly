package me.loki2302.framework;

import java.util.List;

public interface Handler<TParameterMetadata> {
    List<TParameterMetadata> getParameters();
    Object handle(Object instance, List<Object> arguments);
}
