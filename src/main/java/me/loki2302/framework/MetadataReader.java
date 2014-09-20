package me.loki2302.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface MetadataReader<TClassMeta, TMethodMeta, TParameterMeta> {
    TClassMeta readClass(Class<?> clazz);
    TMethodMeta readMethod(TClassMeta classContext, Method method);
    TParameterMeta readParameter(
            TClassMeta classContext,
            TMethodMeta methodContext,
            Class<?> parameterClass,
            Annotation[] parameterAnnotations);
}
