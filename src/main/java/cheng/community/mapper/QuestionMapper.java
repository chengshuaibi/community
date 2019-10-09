package cheng.community.mapper;


import cheng.community.dto.QuestionDTO;
import cheng.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * @author cheng
 * @date 2019/10/5 0005 下午 14:42
 */
@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,tag,gmt_create,gmt_modified,creator)values(#{title},#{description},#{tag},#{gmtCreate},#{gmtModified},#{creator})")
    void create(Question question);
    @Select("select * from question limit #{offSize},#{size}")
    List<Question> list(Integer offSize,Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator=#{userId}")
    Integer countById(Integer userId);

    @Select("select * from question where creator= #{userId}  limit #{offset},#{size}")
    List<Question> listByUserId(@Param("userId") Integer userId, @Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select * from question where id =#{id}")
    Question getById(Integer id);
}