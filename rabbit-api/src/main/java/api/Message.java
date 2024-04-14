package api;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangfin
 * @Date 2024/2/25
 * @Desc 消息配置
 */
@Data
public class Message implements Serializable {

    /**
     * 消息的唯一ID
     */
    private String messageId;

    /**
     * 消息的主题
     */
    private String topic;

    /**
     * 消息的路由规则
     */
    private String routingKey = "";

    private Map<String, Object> attributes = new HashMap<>();

    /**
     * 延迟消息的参数配置
     */
    private int delayMills;

    /**
     * 消息类型
     * 默认为CONFIRM类型
     */
    private String messageType = MessageType.COMFIRM;

    public Message() {

    }

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills, String messageType) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMills = delayMills;
        this.messageType = messageType;
    }

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMills = delayMills;
    }
}
