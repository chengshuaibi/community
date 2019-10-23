package cheng.community.enums;

/**
 * @author cheng
 * @date 2019/10/22 0022 下午 15:51
 */
public enum  NotificationStatusEnum {
   //未读为0，读了为1
    UNREAD(0),
    READ(1)
    ;
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
