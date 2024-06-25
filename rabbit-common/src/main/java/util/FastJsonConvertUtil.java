package util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * @Author wangfin
 * @Date 2024/6/24
 * @Desc java对象与json进行转换的工具类
 */
public class FastJsonConvertUtil {

    private static final SerializerFeature[] FEATURES = {SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero,
            SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullBooleanAsFalse};

    /**
     * 将JSONObject对象转换为实体对象
     * @param json
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T convertJsonToObject(String json, Class<T> clazz) {
        try {
            T t = JSONObject.parseObject(json, clazz);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertObjectToJSON(Object object) {
        try {
            String text = JSONObject.toJSONString(object, FEATURES);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> convertJSONTOArray(String json, Class<T> clazz) {
        try {
            List<T> list = JSONObject.parseArray(json, clazz);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
