package com.personal.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static JsonTransformer instance = null;

    private JsonTransformer() {}

    static JsonTransformer getInstance() {
        if (instance == null) {
            instance = new JsonTransformer();
        }
        return instance;
    }

    @Override
    public String render(Object o) throws Exception {
        return OBJECT_MAPPER.writeValueAsString(o);
    }
}
