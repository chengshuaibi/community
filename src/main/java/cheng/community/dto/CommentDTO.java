package cheng.community.dto;

import lombok.Data;

/**
 * @author cheng
 * @date 2019/10/11 0011 下午 15:07
 */
@Data
public class CommentDTO {
    private long parentId;
    private String content;
    private Integer type;

}
