package api;

import org.springframework.stereotype.Component;

/**
 * @Author wangfin
 * @Date 2024/2/25
 * @Desc 消息类型
 */
@Component
public class MessageType {

    /**
     * 迅速消息：不需要保障消息的可靠性，也不需要做confirm确认
     */
    public final static String RAPID = "0";

    /**
     * 确认消息：不需要保障消息的可靠性，但是会做消息的comfirm确认
     */
    public final static String COMFIRM = "1";

    /**
     * 可靠性消息：一定要保障消息的100%可靠性投递，不允许有任何消息的丢失
     * 保障数据库和所发的消息是原子性的（最终一致的）
     */
    public static final String RELIANT = "2";


}
