package me.solby.xboot.config.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import me.solby.itool.verify.ObjectUtil;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 自定义Jackson2消息转换器
 * <p>
 * 自定义序列化/反序列化可添加内部类实现
 *
 * @author majhdk
 * @date 2019-07-04
 */
public class XJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    /**
     * 默认时间格式
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public XJackson2HttpMessageConverter() {
        this.init();
    }

    /**
     * 初始化配置
     */
    private void init() {
        super.setObjectMapper(
                new Jackson2ObjectMapperBuilder()
                        .indentOutput(true)
                        .deserializerByType(Instant.class, new InstantDeserializer())
                        .serializerByType(Instant.class, new InstantSerializer())
                        .deserializerByType(LocalDate.class, new LocalDateDeserializer())
                        .serializerByType(LocalDate.class, new LocalDateSerializer())
                        .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer())
                        .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer())
                        .serializationInclusion(JsonInclude.Include.NON_NULL)
                        .build()
                        // 进制填充未知属性
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        // 注册模块
                        .registerModules(new Jdk8Module(), new JavaTimeModule(), new ParameterNamesModule())
        );
    }

    /**
     * 自定义Instant时间序列化
     */
    private static class InstantSerializer extends JsonSerializer<Instant> {
        @Override
        public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeObject(null == value ? null : value.atZone(ZoneId.systemDefault()).format(formatter));
        }
    }

    /**
     * 自定义Instant时间反序列化
     */
    private static class InstantDeserializer extends JsonDeserializer<Instant> {
        @Override
        public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            var temporal = formatter.parse(p.getText());
            return ObjectUtil.isEmpty(p.getText()) ? null : LocalDateTime.from(temporal).toInstant(ZoneOffset.UTC);
        }
    }

    /**
     * 自定义LocalDate时间序列化
     */
    private static class LocalDateSerializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeObject(null == value ? null : value.toString());
        }
    }

    /**
     * 自定义LocalDate时间反序列化
     */
    private static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return ObjectUtil.isEmpty(p.getText()) ? null : LocalDate.parse(p.getText());
        }
    }

    /**
     * 自定义LocalDateTime时间序列化
     */
    private static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeObject(null == value ? null : value.toString());
        }
    }

    /**
     * 自定义LocalDateTime时间反序列化
     */
    private static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return ObjectUtil.isEmpty(p.getText()) ? null : LocalDateTime.parse(p.getText(), formatter);
        }
    }

}
