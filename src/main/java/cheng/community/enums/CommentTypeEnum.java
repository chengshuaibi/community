package cheng.community.enums;

/**
 * @author cheng
 * @date 2019/10/11 0011 下午 16:20
 */
public enum  CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type){
        this.type=type;
    }

}
