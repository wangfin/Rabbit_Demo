package api;

/**
 * @Author wangfin
 * @Date 2024/2/25
 * @Desc 回调函数处理
 */
public interface SendCallback {

    void onSuccess();

    void onFailure();
}
