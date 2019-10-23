package cheng.community.enums;

import cheng.community.model.Notification;

/**
 * 可以接受到前端的类型来决定到底时问题类型还是评论类型
 *
 * @author cheng
 * @date 2019/10/11 0011 下午 16:20
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;


    public static boolean isExit(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }


    CommentTypeEnum(Integer type) {
        this.type = type;
    }

}
