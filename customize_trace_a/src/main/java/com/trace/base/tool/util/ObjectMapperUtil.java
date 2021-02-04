package com.trace.base.tool.util;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * jackson ObjectMapper工具类
 *
 * @author ty
 */
public final class ObjectMapperUtil {
    private static final ObjectMapper SNAKE_OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectMapper CAMEL_OBJECT_MAPPER = new ObjectMapper();

    static {
        // java8 Optional/Stream support
        Jdk8Module jdk8Module = new Jdk8Module();
        // java8 Method/Constructor Parameter support
        ParameterNamesModule parameterNamesModule = new ParameterNamesModule(JsonCreator.Mode.DEFAULT);
        // Custom TimeFormat,Default JavaTimeModule doesn't suit;
        SimpleModule module = new SimpleModule();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        module.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        //module.addSerializer(String.class, new StringUnicodeSerializer());
        SNAKE_OBJECT_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        SNAKE_OBJECT_MAPPER.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        SNAKE_OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 使用自带的转义
        SNAKE_OBJECT_MAPPER.enable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        SNAKE_OBJECT_MAPPER.registerModules(module, jdk8Module, parameterNamesModule);
        CAMEL_OBJECT_MAPPER.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        CAMEL_OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        CAMEL_OBJECT_MAPPER.registerModules(module, jdk8Module, parameterNamesModule);
    }

    public static ObjectMapper getSnakeObjectMapper() {
        return SNAKE_OBJECT_MAPPER;
    }

    public static ObjectMapper getCamelObjectMapper() {
        return CAMEL_OBJECT_MAPPER;
    }

    /**
     * 字符串转unicode,避免不同系统编码不一致造成乱码
     *
     * @author ty
     */
    public static class StringUnicodeSerializer extends JsonSerializer<String> {
        private final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
        private final int[] ESCAPE_CODES = CharTypes.get7BitOutputEscapes();

        private void writeUnicodeEscape(JsonGenerator gen, char c) throws IOException {
            gen.writeRaw('\\');
            gen.writeRaw('u');
            gen.writeRaw(HEX_CHARS[(c >> 12) & 0xF]);
            gen.writeRaw(HEX_CHARS[(c >> 8) & 0xF]);
            gen.writeRaw(HEX_CHARS[(c >> 4) & 0xF]);
            gen.writeRaw(HEX_CHARS[c & 0xF]);
        }

        private void writeShortEscape(JsonGenerator gen, char c) throws IOException {
            gen.writeRaw('\\');
            gen.writeRaw(c);
        }

        @Override
        public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            int status = ((JsonWriteContext) gen.getOutputContext()).writeValue();
            switch (status) {
                case JsonWriteContext.STATUS_OK_AFTER_COLON:
                    gen.writeRaw(':');
                    break;
                case JsonWriteContext.STATUS_OK_AFTER_COMMA:
                    gen.writeRaw(',');
                    break;
                case JsonWriteContext.STATUS_EXPECT_NAME:
                    throw new JsonGenerationException("Can not write string value here", gen);
                default:
                    break;
            }
            //写入JSON中字符串的开头引号
            gen.writeRaw('"');
            for (char c : value.toCharArray()) {
                if (c >= 0x80) {
                    // 为所有非ASCII字符生成转义的unicode字符
                    writeUnicodeEscape(gen, c);
                } else {
                    // 为ASCII字符中前128个字符使用转义的unicode字符
                    int code = (c < ESCAPE_CODES.length ? ESCAPE_CODES[c] : 0);
                    if (code == 0) {
                        // 此处不用转义
                        gen.writeRaw(c);
                    } else if (code < 0) {
                        // 通用转义字符
                        writeUnicodeEscape(gen, (char) (-code - 1));
                    } else {
                        // 短转义字符 (\n \t ...)
                        writeShortEscape(gen, (char) code);
                    }
                }
            }
            //写入JSON中字符串的结束引号
            gen.writeRaw('"');
        }
    }
}