package cheng.community.Exception;

/**
 * 自定义异常，方便用枚举类抛异常
 * @author cheng
 * @date 2019/10/10 0010 下午 19:21
 */
public class CustomizeException extends RuntimeException {
        private String message;
        private Integer code;



    public CustomizeException(ICustomizeErrorCode errorCode){
        this.message=errorCode.getMessage();
        this.code=errorCode.getCode();
    }




    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
