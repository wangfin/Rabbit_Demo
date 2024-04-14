package api;

/**
 * @Author wangfin
 * @Date 2024/2/25
 * @Desc 消费者监听消息
 */
public interface MessageListener {

    void onMessage(Message message);
}
