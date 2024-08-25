package top.zhx47.baiduwp.api.baidu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static final ObjectMapper om = new ObjectMapper();

    public static JsonNode readTree(String value) throws JsonProcessingException {
        return om.readTree(value);
    }
}
