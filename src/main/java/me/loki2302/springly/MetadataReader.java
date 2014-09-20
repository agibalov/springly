package me.loki2302.springly;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface MetadataReader<TClassMeta, TMethodMeta, TParameterMeta> {
    TClassMeta readClass(Class<?> clazz, ClassHelper classHelper);
    TMethodMeta readMethod(TClassMeta classContext, Method method, MethodHelper methodHelper);
    TParameterMeta readParameter(
            TClassMeta classContext,
            TMethodMeta methodContext,
            Class<?> parameterClass,
            Annotation[] parameterAnnotations,
            ParameterHelper parameterHelper);
}
