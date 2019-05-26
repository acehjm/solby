package me.solby.itool.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        //todo 添加java8时间支持
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.setDateFormat(new SimpleDateFormat(DATE_FORMAT_PATTERN));
    }

}
