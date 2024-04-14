package broker;


import api.Message;

/**
 * @Author wangfin
 * @Date 2024/3/13
 * @Desc 具体发送不同种类型消息的接口
 */
public interface RabbitBroker {

    void rapidSend(Message message);

    void confirmSend(Message message);

    void reliantSend(Message message);
}
