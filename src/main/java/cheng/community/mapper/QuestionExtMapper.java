package cheng.community.mapper;

import cheng.community.model.Question;

import java.util.List;

/**
 * @author cheng
 * @date 2019/10/11 0011 下午 13:40
 */
public interface QuestionExtMapper {
    int incView(Question question);
    int incCommentCount(Question question);
    List<Question> selectRelated(Question question);
}
