package me.loki2302;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.loki2302.framework.*;
import me.loki2302.web.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class App {
    public static void main(String[] args) throws JsonProcessingException {
        ControllerMetadataReader controllerMetadataReader = new ControllerMetadataReader();
        ControllerHandlerFactory controllerHandlerFactory = new ControllerHandlerFactory();
        HandlerReader<ControllerClassMetadata, ControllerMethodMetadata, ControllerParameterMetadata, ControllerHandler> classReader =
                new HandlerReader<ControllerClassMetadata, ControllerMethodMetadata, ControllerParameterMetadata, ControllerHandler>(
                        controllerMetadataReader, controllerHandlerFactory);

        HandlerPredicate<ControllerHandler, ControllerRequest> handlerPredicate = new ControllerHandlerPredicate();
        HandlerRegistry<ControllerHandler, ControllerRequest> controllerHandlerRegistry =
                new HandlerRegistry<ControllerHandler, ControllerRequest>(handlerPredicate);

        HandlerMethodArgumentResolverRegistry<ControllerParameterMetadata, ControllerRequest> handlerMethodArgumentResolverRegistry =
                new HandlerMethodArgumentResolverRegistry<ControllerParameterMetadata, ControllerRequest>();
        handlerMethodArgumentResolverRegistry.register(new PathParamHandlerMethodArgumentResolver());
        handlerMethodArgumentResolverRegistry.register(new QueryParamHandlerMethodArgumentResolver());

        RequestProcessor<ControllerHandler, ControllerParameterMetadata, ControllerRequest> controllerRequestProcessor =
                new RequestProcessor<ControllerHandler, ControllerParameterMetadata, ControllerRequest>(controllerHandlerRegistry, handlerMethodArgumentResolverRegistry);

        List<ControllerHandler> controllerHandlers = classReader.readClass(MyController.class);
        controllerHandlerRegistry.register(controllerHandlers);

        /*ControllerRequest controllerRequest = new ControllerRequest();
        controllerRequest.path = "/api/1";
        controllerRequest.pathParams = Collections.<String, Object>singletonMap("x", 123);
        controllerRequest.queryParams = Collections.<String, Object>singletonMap("y", "hello");
        RunHandlerAction<ControllerHandler> runHandlerAction = controllerRequestProcessor.processRequest(controllerRequest);

        Object result = runHandlerAction.run(new MyController());
        System.out.printf("actionOne says: %s\n", result);*/

        ControllerRequest controllerRequest = new ControllerRequest();
        controllerRequest.path = "/api/addNumbers";
        controllerRequest.pathParams = new HashMap<String, Object>() {{
            put("x", 2);
            put("y", 13);
        }};

        controllerRequest.queryParams = Collections.<String, Object>singletonMap("y", "hello");
        RunHandlerAction<ControllerHandler> runHandlerAction = controllerRequestProcessor.processRequest(controllerRequest);

        Object result = runHandlerAction.run(new MyController());
        System.out.printf("addNumbers says: %s\n", result);
    }
}
