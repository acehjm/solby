package me.solby.itool.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.text.SimpleDateFormat;

/**
 * @author majhdk
 * @DESCRIPTION 自定义ObjectMapper
 * @date 2018-12-01
 */
class CustomObjectMapper extends ObjectMapper {

    /**
     * 通用时间格式
     */
    private static final String DATE_FORMAT_PATTERN = "yyyy-mm-dd HH:mm:ss";

    CustomObjectMapper() {
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.setDateFormat(new SimpleDateFormat(DATE_FORMAT_PATTERN));

        //models
        this.registerModules(new Jdk8Module(), new JavaTimeModule(), new ParameterNamesModule());
    }

}
