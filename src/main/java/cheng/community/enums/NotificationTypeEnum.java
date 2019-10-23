package cheng.community.enums;

/**
 * @author cheng
 * @date 2019/10/22 0022 下午 15:38
 */
public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论"),
    ;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    private  int type;
    private String name;

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
    public static String nameOftype(int type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
