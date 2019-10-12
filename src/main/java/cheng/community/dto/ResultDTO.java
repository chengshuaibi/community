package cheng.community.dto;

import cheng.community.Exception.CustomizeErrorCode;
import cheng.community.Exception.ICustomizeErrorCode;
import lombok.Data;

/**
 * 评论成功或者失败的判断
 * @author cheng
 * @date 2019/10/11 0011 下午 16:03
 */
@Data
public class ResultDTO {
    private  int code;
    private String message;

    public ResultDTO() {
    }
    public static ResultDTO errorOf(CustomizeErrorCode customizeErrorCode) {
        return  errorOf(customizeErrorCode.getCode(),customizeErrorCode.getMessage());
    }

    public  static ResultDTO errorOf(Integer code, String message){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return  resultDTO;

    }
}
