package cheng.community.model;

import lombok.Data;

/**
 * @author cheng
 * @date 2019/10/5 0005 下午 15:34
 */
@Data
public class Question {
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
}
