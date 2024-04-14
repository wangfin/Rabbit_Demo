package serializer;

/**
 * @Author wangfin
 * @Date 2024/3/29
 * @Desc 序列化和反序列化
 */
public interface Serializer {

    byte[] serializeRaw(Object data);

    String serialize(Object data);

    <T> T deserialize(String content);

    <T> T deserialize(byte[] content);
}
