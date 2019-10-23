package cheng.community.dto;

import cheng.community.model.User;
import lombok.Data;

/**
 * @author cheng
 * @date 2019/10/22 0022 下午 16:25
 */
@Data
public class NotificationDTO {
    private Long id;

    private Long gmtCreate;

    private Integer status;

    private long notifier;//通知的人

    private String outerTitle;

    private String typeName;

    private String notifierName;

    private Long  outerid;

    private Integer type;

}
