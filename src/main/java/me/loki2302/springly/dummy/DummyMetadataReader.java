package me.loki2302.springly.dummy;

import me.loki2302.springly.ClassHelper;
import me.loki2302.springly.MetadataReader;
import me.loki2302.springly.MethodHelper;
import me.loki2302.springly.ParameterHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyMetadataReader implements MetadataReader<Map<String, Object>, Map<String, Object>, Map<String, Object>, DummyHandler> {
    @Override
    public Map<String, Object> readClass(
            Class<?> clazz,
            ClassHelper classHelper) {

        if(classHelper.isAbstract() || !classHelper.isPublic()) {
            return null;
        }

        Map<String, Object> classMetadata = new HashMap<String, Object>();
        classMetadata.put("handlerClass", clazz);
        return classMetadata;
    }

    @Override
    public Map<String, Object> readMethod(
            Map<String, Object> classContext,
            Method method,
            MethodHelper methodHelper) {

        if(methodHelper.isAbstract() || !methodHelper.isPublic()) {
            return null;
        }

        DummyAction dummyAction = methodHelper.getAnnotation(DummyAction.class);
        if(dummyAction == null) {
            return null;
        }

        Map<String, Object> methodMetadata = new HashMap<String, Object>();
        methodMetadata.put("actionName", dummyAction.value());
        methodMetadata.put("method", method);
        return methodMetadata;
    }

    @Override
    public Map<String, Object> readParameter(
            Map<String, Object> classContext,
            Map<String, Object> methodContext,
            Class<?> parameterClass,
            Annotation[] parameterAnnotations,
            ParameterHelper parameterHelper) {

        DummyParam dummyParam = parameterHelper.getAnnotation(DummyParam.class);
        if(dummyParam == null) {
            return null;
        }

        Map<String, Object> paramMetadata = new HashMap<String, Object>();
        paramMetadata.put("paramName", dummyParam.value());
        return paramMetadata;
    }

    @Override
    public DummyHandler makeHandler(
            Map<String, Object> classMeta,
            Map<String, Object> methodMeta,
            List<Map<String, Object>> parametersMeta) {

        String actionName = (String)methodMeta.get("actionName");
        Method method = (Method)methodMeta.get("method");
        Class<?> handlerClass = (Class<?>)classMeta.get("handlerClass");

        DummyHandler dummyHandler = new DummyHandler(
                actionName,
                method,
                handlerClass,
                parametersMeta);

        return dummyHandler;
    }
}
