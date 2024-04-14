package api;

import exception.MessageRunTimeException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author wangfin
 * @Date 2024/2/25
 * @Desc 建造者模式
 */
public class MessageBuilder {

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

    private  MessageBuilder() {

    }

    public static MessageBuilder create() {
        return new MessageBuilder();
    }

    public MessageBuilder withMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public MessageBuilder withTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public MessageBuilder withRoutingKey(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

    public MessageBuilder withAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        return this;
    }

    public MessageBuilder withAttribute(String key, Object value) {
        this.attributes.put(key, value);
        return this;
    }

    public MessageBuilder withDelayMills(Integer delayMills) {
        this.delayMills = delayMills;
        return this;
    }

    public MessageBuilder withMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public Message build() {
        if (StringUtils.isBlank(messageId)) {
            messageId = UUID.randomUUID().toString();
        }

        if (StringUtils.isBlank(topic)) {
            throw new MessageRunTimeException("topic为空");
        }

        Message message = new Message(messageId, topic, routingKey, attributes, delayMills, messageType);
        return message;
    }


}
