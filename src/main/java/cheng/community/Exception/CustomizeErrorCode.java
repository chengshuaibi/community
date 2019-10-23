package cheng.community.Exception;

import lombok.Data;

/**
 * 自定义的枚举类实现
 * @author cheng
 * @date 2019/10/10 0010 下午 19:56
 */

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NO_FIND(2001,"你要找的问题不存在,换个问题试试？"),
    TARGET_PARAM_NO_FIND(2002,"未选中任何问题或者评论"),
    NO_LOGIN(2003,"当前操作需要登录请登录后重试"),
    SYSTEM_ERROR(2004,"服务器炸了，真的炸了，没骗你!!!"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或者不存在"),
    COMMENT_NO_FIND(2006,"回复的评论不存在,要不要换个试试"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"非法操作!你的用户和当前信息所属用户不一致"),
    NOTIFICATION_NO_FIND(2009,"消息不存在");


    private String message;
    private Integer code;
    CustomizeErrorCode(Integer code,String message){
        this.message=message;
        this.code=code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
