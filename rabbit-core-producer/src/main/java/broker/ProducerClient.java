package broker;

import api.Message;
import api.MessageProducer;
import api.MessageType;
import api.SendCallback;
import exception.MessageRunTimeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author wangfin
 * @Date 2024/2/28
 * @Desc 发送消息的实际实现类
 */
@Component
public class ProducerClient implements MessageProducer {

    @Autowired
    private RabbitBroker rabbitBroker;

    @Override
    public void send(Message message) throws MessageRunTimeException {
        if (StringUtils.isBlank(message.getTopic())) {
            throw new NullPointerException();
        }

        String messageType = message.getMessageType();
        switch (messageType) {
            case MessageType.RAPID:
                rabbitBroker.rapidSend(message);
                break;
            case MessageType.COMFIRM:
                rabbitBroker.confirmSend(message);
                break;
            case MessageType.RELIANT:
                rabbitBroker.reliantSend(message);
                break;
            default:
                break;
        }

    }

    @Override
    public void send(List<Message> messages) throws MessageRunTimeException {
        messages.forEach(message -> {
            try {
                message.setMessageType(MessageType.RAPID);
                MessageHolder.add(message);
                send(message);
            } catch (MessageRunTimeException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void send(Message message, SendCallback sendCallback) throws MessageRunTimeException {

    }
}
