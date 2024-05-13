package service;

import entity.BrokerMessage;
import mapper.BrokerMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
