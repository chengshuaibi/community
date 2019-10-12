package cheng.community.service;

import cheng.community.Exception.CustomizeErrorCode;
import cheng.community.Exception.CustomizeException;
import cheng.community.Exception.ICustomizeErrorCode;
import cheng.community.mapper.CommentMapper;
import cheng.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cheng
 * @date 2019/10/11 0011 下午 16:24
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    public void insert(Comment comment) {
        if (comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NO_FIND);
        }
        commentMapper.insert(comment);
    }
}
