package me.loki2302.springly;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HandlerReader<TClassMeta, TMethodMeta, TParameterMeta, THandler extends Handler> {
    private final MetadataReader<TClassMeta, TMethodMeta, TParameterMeta> metadataReader;
    private final HandlerFactory<TClassMeta, TMethodMeta, TParameterMeta, THandler> handlerFactory;

    public HandlerReader(
            MetadataReader<TClassMeta, TMethodMeta, TParameterMeta> metadataReader,
            HandlerFactory<TClassMeta, TMethodMeta, TParameterMeta, THandler> handlerFactory) {

        this.metadataReader = metadataReader;
        this.handlerFactory = handlerFactory;
    }

    public List<THandler> readClass(Class<?> clazz) {
        List<THandler> handlers = new ArrayList<THandler>();
        TClassMeta classContext = metadataReader.readClass(clazz);
        if(classContext == null) {
            return handlers;
        }

        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods) {
            TMethodMeta methodContext = metadataReader.readMethod(classContext, method);
            if(methodContext == null) {
                continue;
            }

            List<TParameterMeta> parameterContexts = new ArrayList<TParameterMeta>();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Annotation[][] parameterAnnotationArrays = method.getParameterAnnotations();
            int numberOfParameters = parameterTypes.length;
            for(int i = 0; i < numberOfParameters; ++i) {
                Class<?> parameterClass = parameterTypes[i];
                Annotation[] parameterAnnotations = parameterAnnotationArrays[i];
                TParameterMeta parameterContext = metadataReader.readParameter(classContext, methodContext, parameterClass, parameterAnnotations);
                if(parameterContext == null) {
                    continue;
                }

                parameterContexts.add(parameterContext);
            }

            if(parameterContexts.size() != numberOfParameters) {
                continue;
            }

            THandler handler = handlerFactory.makeHandler(classContext, methodContext, parameterContexts);
            handlers.add(handler);
        }

        return handlers;
    }
}
