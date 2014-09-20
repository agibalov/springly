package me.loki2302;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.loki2302.springly.RequestProcessor;
import me.loki2302.springly.dummy.DummyAction;
import me.loki2302.springly.dummy.DummyParam;
import me.loki2302.springly.dummy.Springly;

import java.util.HashMap;

public class App {
    public static void main(String[] args) throws JsonProcessingException {
        Calculator calculator = new Calculator();
        RequestProcessor requestProcessor = Springly.makeRequestProcessor(calculator);

        Object result = requestProcessor.processRequest(new HashMap<String, Object>() {{
            put("action", "addNumbers");
            put("a", 2);
            put("b", 3);
        }});

        System.out.println(result);
    }

    public static class Calculator {
        @DummyAction("addNumbers")
        public int addNumbers(
                @DummyParam("a") int a,
                @DummyParam("b") int b) {

            return a + b;
        }

        @DummyAction("subNumbers")
        public int subNumbers(
                @DummyParam("a") int a,
                @DummyParam("b") int b) {

            return a - b;
        }
    }
}
