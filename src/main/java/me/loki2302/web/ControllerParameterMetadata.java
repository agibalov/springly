package me.loki2302.web;

import java.lang.annotation.Annotation;

public class ControllerParameterMetadata {
    public String name;
    public Class<?> type;
    public Annotation[] annotations;
}
