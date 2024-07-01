import broker.RabbitBroker;
import constant.BrokerMessageStatus;
import entity.BrokerMessage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.MessageStoreService;

import java.util.List;

/**
 * @Author wangfin
 * @Date 2024/6/30
 * @Desc
 */

// 需要引用dangdang 的 elastic-job-lite-spring-boot-starter
@Component
public class RetryMessageDataFlowJob implements DataFlowJob<BrokerMessage>{

    @Autowired
    private MessageStoreService messageStoreService;

    @Autowired
    private RabbitBroker rabbitBroker;

    private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RetryMessageDataFlowJob.class);
    @Override
    public boolean execute(ShardingContext shardingContext, List<BrokerMessage> list) {
        list.forEach(brokerMessage -> {
            if (brokerMessage.getTryCount() >= 3) {
                // 重试次数过多，直接置为失败
                this.messageStoreService.failure(brokerMessage.getMessageId());
                LOGGER.warn("消息重试次数过多，直接置为失败，消息ID:{}", brokerMessage.getMessageId());
            } else {
                // 每次重发的时候需要更新一下try count字段
                this.messageStoreService.update4TryCount(brokerMessage.getMessageId());

                // 重发消息
                this.rabbitBroker.reliantSend(brokerMessage.getMessage());
            }
        });
        return false;
    }

    @Override
    public List<BrokerMessage> fetchData(ShardingContext shardingContext) {
        List<BrokerMessage> list = messageStoreService.fetchTimeOutMessage4Retry(BrokerMessageStatus.SENDING.getCode());
        return list;
    }
}
