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
        ClassHelper classHelper = new ClassHelper(clazz);
        TClassMeta classContext = metadataReader.readClass(clazz, classHelper);
        if(classContext == null) {
            return handlers;
        }

        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods) {
            MethodHelper methodHelper = new MethodHelper(method);
            TMethodMeta methodContext = metadataReader.readMethod(
                    classContext,
                    method,
                    methodHelper);
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
                ParameterHelper parameterHelper = new ParameterHelper(parameterClass, parameterAnnotations);
                TParameterMeta parameterContext = metadataReader.readParameter(
                        classContext,
                        methodContext,
                        parameterClass,
                        parameterAnnotations,
                        parameterHelper);
                if(parameterContext == null) {
                    continue;
                }

                parameterContexts.add(parameterContext);
            }

            if(parameterContexts.size() != numberOfParameters) {
                continue;
            }

            THandler handler = handlerFactory.makeHandler(
                    classContext,
                    methodContext,
                    parameterContexts);
            handlers.add(handler);
        }

        return handlers;
    }
}
