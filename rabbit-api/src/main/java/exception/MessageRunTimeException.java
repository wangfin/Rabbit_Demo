package exception;

/**
 * @Author wangfin
 * @Date 2024/2/25
 * @Desc 处理消息异常
 */
public class MessageRunTimeException extends RuntimeException {

    public MessageRunTimeException() {
        super();
    }

    public MessageRunTimeException(String message) {
        super(message);
    }

    public MessageRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageRunTimeException(Throwable cause) {
        super(cause);
    }


}
