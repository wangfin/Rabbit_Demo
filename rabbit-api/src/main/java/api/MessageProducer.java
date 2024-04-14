package api;

import exception.MessageRunTimeException;

import java.util.List;

/**
 * @Author wangfin
 * @Date 2024/2/25
 * @Desc 消息生产者
 */
public interface MessageProducer {

    /**
     * 消息的异步发送方法 附带SendCallback回调执行响应的业务逻辑处理
     * @param message
     * @param sendCallback
     * @throws MessageRunTimeException
     */
    void send(Message message, SendCallback sendCallback) throws MessageRunTimeException;

    /**
     * 消息发送
     * @param message
     * @throws MessageRunTimeException
     */
    void send(Message message) throws MessageRunTimeException;

    /**
     * 批量消息发送
     * @param messages
     * @throws MessageRunTimeException
     */
    void send(List<Message> messages) throws MessageRunTimeException;
}
