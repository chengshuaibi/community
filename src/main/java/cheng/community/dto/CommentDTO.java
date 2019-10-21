package cheng.community.dto;

import cheng.community.model.User;
import lombok.Data;

/**
 * @author cheng
 * @date 2019/10/14 0014 下午 15:46
 */
@Data
public class CommentDTO {


        private Long id;

        private Long parentId;

        private Integer type;

        private Long commentator;

        private Long gmtCreate;

        private Long gmtModified;

        private Long likeCount;

        private String content;

        private Integer commentCount;

        private User user;
}
