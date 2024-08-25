package top.zhx47.baiduwp.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static final ObjectMapper om = new ObjectMapper();

    public static String toJsonString(Object value) {
        try {
            return om.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
