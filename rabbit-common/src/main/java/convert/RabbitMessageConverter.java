package convert;

import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.lang.reflect.Type;

/**
 * @Author wangfin
 * @Date 2024/4/2
 * @Desc 装饰者
 */
public class RabbitMessageConverter implements MessageConverter {

    private GenericMessageConverter delegate;

    private final String defaultExpire = String.valueOf(24 * 60 * 60 * 1000);

    public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
        Preconditions.checkNotNull(genericMessageConverter);
        this.delegate = genericMessageConverter;
    }


    @Override
    public Message toMessage(Object object, MessageProperties messageProperties, Type genericType) throws MessageConversionException {
        return MessageConverter.super.toMessage(object, messageProperties, genericType);
    }

    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        messageProperties.setExpiration(defaultExpire);
        return this.delegate.toMessage(o, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        api.Message msg = (api.Message) this.delegate.fromMessage(message);
        return msg;
    }
}
