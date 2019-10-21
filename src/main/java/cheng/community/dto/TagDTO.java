package cheng.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author cheng
 * @date 2019/10/21 0021 上午 11:20
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;

}
