package serializer.impl;

import api.Message;
import serializer.Serializer;
import serializer.SerializerFactory;

/**
 * @Author wangfin
 * @Date 2024/3/29
 * @Desc
 */
public class JacksonSerializeFactory implements SerializerFactory {

    public static final JacksonSerializeFactory INSTANCE = new JacksonSerializeFactory();
    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }
}
