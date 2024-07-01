package service;

import constant.BrokerMessageStatus;
import entity.BrokerMessage;
import mapper.BrokerMessageMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author wangfin
 * @Date 2024/5/13
 * @Desc
 */
@Service
public class MessageStoreService {

    @Autowired
    private BrokerMessageMapper brokerMessageMapper;

    public int insert(BrokerMessage brokerMessage) {
        return brokerMessageMapper.insert(brokerMessage);
    }

    public BrokerMessage selectByMessageId(String messageId) {
        return brokerMessageMapper.selectByPrimaryKey(messageId);
    }

    public void success(String messageId) {
        brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_SUCCESS.getCode(), new Date());
    }

    public void failure(String messageId) {
        brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_FAIL.getCode(), new Date());
    }

    public List<BrokerMessage> fetchTimeOutMessage4Retry(String brokerMessageStatus) {
        return brokerMessageMapper.queryBrokerMessageStatus4TimeOut(brokerMessageStatus);
    }

    public Integer update4TryCount(String brokerMessageId) {
        return brokerMessageMapper.update4TryCount(brokerMessageId, new Date());
    }


}
