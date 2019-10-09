package cheng.community.dto;

import lombok.Data;

/**
 * @author cheng
 * @date 2019/9/28 0028 上午 11:31
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String  code;
    private String redirect_uri;
    private String state;


}
