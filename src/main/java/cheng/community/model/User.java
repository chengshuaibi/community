package cheng.community.model;

import lombok.Data;

/**
 * @author cheng
 * @date 2019/10/5 0005 上午 8:23
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private long gmtcreate;
    private long gmtmodified;
    private String avatarUrl;


}
