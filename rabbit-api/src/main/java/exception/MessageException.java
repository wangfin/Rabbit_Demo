package exception;

/**
 * @Author wangfin
 * @Date 2024/2/25
 * @Desc 处理消息异常
 */
public class MessageException extends Exception {

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }


}
