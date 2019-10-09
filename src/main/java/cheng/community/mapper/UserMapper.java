package cheng.community.mapper;

import cheng.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author cheng
 * @date 2019/10/5 0005 上午 8:19
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url)values(#{accountId},#{name},#{token},#{gmtcreate},#{gmtmodified},#{avatarUrl})")
    public void insert(  User user);

    @Select("select * from user where token=#{token}")
    User findByToken( String token);
    @Select("select * from user where id=#{id}")
    User findById( Integer id);
}
