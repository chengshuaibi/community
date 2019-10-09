package cheng.community.dto;

import cheng.community.model.User;
import lombok.Data;

/**
 * @author cheng
 * @date 2019/10/7 0007 上午 11:49
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
