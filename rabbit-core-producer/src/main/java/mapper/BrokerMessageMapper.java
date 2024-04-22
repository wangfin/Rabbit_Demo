package mapper;

import entity.BrokerMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author wangfin
 * @Date 2024/4/23
 * @Desc mapper
 */
@Component
public interface BrokerMessageMapper {

    int deleteByPrimaryKey(String messageId);
    int insert(BrokerMessage record);
    int insertSelective(BrokerMessage record);
    BrokerMessage selectByPrimaryKey(String messageId);
    int updateByPrimaryKeySelective(BrokerMessage record);
    int updateByPrimaryKeyWithBLOBs(BrokerMessage record);
    int updateByPrimaryKey(BrokerMessage record);
    void changeBrokerMessageStatus(@Param("brokerMessageId") String brokerMessageId, @Param("status") String status, @Param("updateTime") Date updateTime);
}
