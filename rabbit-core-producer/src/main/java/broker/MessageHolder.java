package broker;

import api.Message;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author wangfin
 * @Date 2024/7/1
 * @Desc 批量消息存储 ThreadLocal
 */
public class MessageHolder {

    private List<Message> messages = Lists.newArrayList();

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static final ThreadLocal<MessageHolder> holder = new ThreadLocal<MessageHolder>() {
        @Override
        protected MessageHolder initialValue() {
            return new MessageHolder();
        }
    };

    public static void add(Message message) {
        holder.get().messages.add(message);
    }

    public static List<Message> clear() {
        List<Message> tmp = Lists.newArrayList(holder.get().messages);
        holder.remove();
        return tmp;
    }

}
