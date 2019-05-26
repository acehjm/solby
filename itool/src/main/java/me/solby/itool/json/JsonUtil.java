package me.solby.itool.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * @author majhdk
 * @DESCRIPTION Json转换工具类
 * @date 2018-12-01
 */
public class JsonUtil {

    /**
     * 自定义Json对象映射类
     */
    private static ObjectMapper objectMapper = new CustomObjectMapper();

    private JsonUtil() {
    }

    /**
     * 转为Json
     *
     * @param pojo 实体对象
     * @param <T>  泛型对象
     * @return
     */
    public static <T> String toJson(T pojo) {
        try {
            return objectMapper.writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            throw new JsonException("transform json string error", e);
        }
    }

    /**
     * Json转为目标对象
     *
     * @param json json数据
     * @param type 目标类对象
     * @param <T>  泛型对象
     * @return
     */
    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            throw new JsonException("parse json object error", e);
        }
    }

    /**
     * json转为目标对象
     *
     * @param json json数据
     * @param type 目标对象
     * @param <T>  泛型类型
     * @return
     */
    public static <T> T toPojo(String json, TypeReference<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            throw new JsonException("parse json pojo error", e);
        }
    }

    /**
     * json转为集合目标对象
     *
     * @param json json数据
     * @param type 目标对象
     * @param <T>  泛型类型
     * @return
     */
    public static <T> T toCollection(String json, TypeReference<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            throw new JsonException("parse json collection error", e);
        }
    }

    /**
     * json转为目标对象
     *
     * @param json  json数据
     * @param outer outer对象
     * @param inner inner对象
     * @param <T>   泛型对象
     * @return
     */
    public static <T> T toPojo(String json, Class outer, Class inner) {
        JavaType javaType = objectMapper.getTypeFactory()
                .constructParametricType(outer, inner);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new JsonException("parse json pojo with class type error", e);
        }
    }

    /**
     * json转为Map对象
     *
     * @param json json数据
     * @return
     */
    public static Map<String, Object> jsonToMap(String json) {
        try {
            TypeReference type = new TypeReference<Map<String, Object>>() {
            };
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            throw new JsonException("json parse map error", e);
        }
    }

    /**
     * Map对象转为目标类对象
     *
     * @param map  map数据
     * @param type 目标类对象
     * @param <T>  泛型类型
     * @return
     */
    public static <T> T mapToPojo(Map map, Class<T> type) {
        return objectMapper.convertValue(map, type);
    }

    /**
     * Map对象转为目标类对象
     *
     * @param map  map数据
     * @param type 目标类对象
     * @param <T>  泛型类型
     * @return
     */
    public static <T> T mapToPojo(Map map, TypeReference<T> type) {
        return objectMapper.convertValue(map, type);
    }

}
