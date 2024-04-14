package broker;

import api.Message;
import api.MessageType;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import convert.GenericMessageConverter;
import convert.RabbitMessageConverter;
import exception.MessageException;
import exception.MessageRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import serializer.Serializer;
import serializer.SerializerFactory;
import serializer.impl.JacksonSerializeFactory;

/**
 * @Author wangfin
 * @Date 2024/3/21
 * @Desc rabbitTemplate池化封装
 *
 * 每一个topic对应一个RabbitTemplate
 * 1. 提高发送效率
 * 2. 可以根据不同的需求制定化不同的RabbitTemplate
 */
@Slf4j
@Component
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback{

    /**
     * rabbitTemplate 发送消息使用单例模式，效率不高，改为池化操作
     */
    // 这里的map的Key为 Topic
    private Map<String, RabbitTemplate> rabbitMap = Maps.newConcurrentMap();

    private Splitter splitter = Splitter.on("#");

    @Autowired
    private ConnectionFactory connectionFactory;

    private SerializerFactory serializerFactory = JacksonSerializeFactory.INSTANCE;

    public RabbitTemplate getTemplate(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message);
        // topic 做key
        String topic = message.getTopic();
        RabbitTemplate rabbitTemplate = rabbitMap.get(topic);
        if (rabbitTemplate != null) {
            return rabbitTemplate;
        }
        log.info("#RabbitTemplateContainer.getTemplate #topic: {} is not exists, create one", topic);

        RabbitTemplate newRabbitTemplate = new RabbitTemplate(connectionFactory);
        newRabbitTemplate.setExchange(topic);
        newRabbitTemplate.setRoutingKey(message.getRoutingKey());
        newRabbitTemplate.setRetryTemplate(new RetryTemplate());

        // 添加序列化和反序列化和converter对象
        Serializer serializer = serializerFactory.create();
        GenericMessageConverter gmc = new GenericMessageConverter(serializer);
        RabbitMessageConverter rmc = new RabbitMessageConverter(gmc);
        // 对于message的序列化方式
        newRabbitTemplate.setMessageConverter(rmc);

        // 根据messageType类型选择是否需要实现callback
        String messageType = message.getMessageType();
        if (!MessageType.RAPID.equals(messageType)) {
            newRabbitTemplate.setConfirmCallback(this);
        }

        rabbitMap.putIfAbsent(topic, newRabbitTemplate);

        return rabbitMap.get(topic);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        // 具体的消息应答

        // correlationData.getId() 内容为#分割，前面为messageId，后面为当前时间戳
        List<String> strings = splitter.splitToList(correlationData.getId());

        String messageId = strings.get(0);
        long sendTime = Long.parseLong(strings.get(1));

        if (ack) {
            log.info("send message is ok, confirm messageId: {}, sendTime: {}", messageId, sendTime);
        } else {
            log.info("send message is fail, confirm messageId: {}, sendTime: {}", messageId, sendTime);
        }
    }
}
