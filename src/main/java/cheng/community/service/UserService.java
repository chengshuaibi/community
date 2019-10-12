package cheng.community.service;

import cheng.community.mapper.UserMapper;
import cheng.community.model.User;
import cheng.community.model.UserExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cheng
 * @date 2019/10/9 0009 下午 20:26
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        //查询是否在数据库中为空
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> dbusers = userMapper.selectByExample(userExample);
        if (dbusers.size() == 0) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            //插入数据
            userMapper.insert(user);
        } else {
            //更新数据
            //以下三个参数可能会变化
            User dbuser = dbusers.get(0);
            User updateUser = new User();

            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setToken(user.getToken());
            updateUser.setName(user.getName());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbuser.getId());
            userMapper.updateByExampleSelective(updateUser,example);

        }
    }


}
