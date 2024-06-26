package constant;

/**
 * @Author wangfin
 * @Date 2024/5/13
 * @Desc
 */
public enum BrokerMessageStatus {

    SENDING("0", "发送中"),
    SEND_SUCCESS("1", "发送成功"),
    SEND_FAIL("2", "发送失败"),
    SEND_FAIL_A_MOMENT("3", "发送失败，稍后重试"),
    CONSUMED_SUCCESS("4", "消费成功"),
    CONSUMED_FAIL("5", "消费失败");

    private String code;
    private String desc;

    BrokerMessageStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
