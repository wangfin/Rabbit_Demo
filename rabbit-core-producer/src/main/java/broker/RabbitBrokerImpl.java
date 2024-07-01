package broker;

import api.Message;
import api.MessageType;
import constant.BrokerMessageConst;
import constant.BrokerMessageStatus;
import entity.BrokerMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.MessageStoreService;

import java.util.Date;
import java.util.List;

/**
 * @Author wangfin
 * @Date 2024/3/13
 * @Desc RabbitBroker 真正发生不同类型消息的实现类
 */

@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker{

//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitTemplateContainer rabbitTemplateContainer;

    @Autowired
    private MessageStoreService messageStoreService;

    /**
     * 迅速发消息
     * @param message
     */
    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
    }

    /**
     * sendKernel 发送消息的核心方法
     * 使用异步线程池发送消息
     */
    private void sendKernel(Message message) {

        AsyncBaseQueue.submit((Runnable) () -> {
            CorrelationData correlationData =
                    new CorrelationData(String.format("%s#%s#%s", message.getMessageId(), System.currentTimeMillis(), message.getMessageType()));
            String topic = message.getTopic();
            String routingKey = message.getRoutingKey();

            // 从池化里面取RabbitTemplate
            RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);

            rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
            log.info("#sendKernel# send to rabbitmq, messageId:{}", message.getMessageId());
        });

    }

    @Override
    public void reliantSend(Message message) {
        message.setMessageType(MessageType.RELIANT);

        BrokerMessage bm = messageStoreService.selectByMessageId(message.getMessageId());
        // 数据库中没有这条数据才会进行重发
        if (bm == null) {
            // 1. 把消息持久化到数据库
            BrokerMessage brokerMessage = new BrokerMessage();
            brokerMessage.setMessageId(message.getMessageId());
            brokerMessage.setStatus(BrokerMessageStatus.SENDING.getCode());

            // tryCount 在最开始发送的时候不需要进行设置
            // 最大容忍时间
            brokerMessage.setNextRetry(DateUtils.addMinutes(new Date(), BrokerMessageConst.TIMEOUT));
            brokerMessage.setCreateTime(new Date());
            brokerMessage.setUpdateTime(new Date());
            brokerMessage.setMessage(message);

            messageStoreService.insert(brokerMessage);
        }

        // 2. 发送消息
        sendKernel(message);
    }

    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.COMFIRM);
        sendKernel(message);
    }

    @Override
    public void sendMessages() {
        List<Message> messages = MessageHolder.clear();
        messages.forEach(message -> {
            MessageHolderAsyncQueue.submit((Runnable) () -> {
                CorrelationData correlationData =
                        new CorrelationData(String.format("%s#%s#%s", message.getMessageId(), System.currentTimeMillis(), message.getMessageType()));
                String topic = message.getTopic();
                String routingKey = message.getRoutingKey();

                // 从池化里面取RabbitTemplate
                RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);

                rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
                log.info("#RabbitBrokerImpl.sendMessages# send to rabbitmq, messageId:{}", message.getMessageId());
            });
        });
    }
}
