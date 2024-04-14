package broker;

import api.Message;
import api.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                    new CorrelationData(String.format("%s#%s", message.getMessageId(), System.currentTimeMillis()));
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

    }

    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.COMFIRM);
        sendKernel(message);
    }
}
