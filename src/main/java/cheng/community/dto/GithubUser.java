package cheng.community.dto;

import lombok.Data;

/**
 * @author cheng
 * @date 2019/9/28 0028 下午 18:55
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
