package cheng.community.service;

import cheng.community.Exception.CustomizeErrorCode;
import cheng.community.Exception.CustomizeException;
import cheng.community.dto.CommentDTO;
import cheng.community.enums.CommentTypeEnum;
import cheng.community.enums.NotificationStatusEnum;
import cheng.community.enums.NotificationTypeEnum;
import cheng.community.mapper.*;
import cheng.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cheng
 * @date 2019/10/11 0011 下午 16:24
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;


    @Transactional
    public void insert(Comment comment, User commentor) {
        if (comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NO_FIND);
        }
        if (comment.getType()==null||!CommentTypeEnum.isExit(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }if (comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());//判断是否有父评论，没有的话肯定是错的
            if (dbComment==null){
                throw new  CustomizeException(CustomizeErrorCode.COMMENT_NO_FIND);
            }   Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());//查询评论所属的问题
            if (question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NO_FIND);
            }
            commentMapper.insert(comment);
            //增加的评论数,设置父类id，用来增加父类中的回复数
            Comment parentComment=new Comment();//父评论
            parentComment.setId(comment.getParentId());
            //设置每次增加的大小为1
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);
            //插入通知
            createNotify(comment, dbComment.getParentId(), commentor.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT.getType(),question.getId());

        }else{
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NO_FIND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
                createNotify(comment,question.getCreator(),commentor.getName(),question.getTitle(), NotificationTypeEnum.REPLY_QUESTION.getType(),question.getId());
        }
    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, int type, Long outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(type);//设置通知类型，是回复了评论还是回复了问题
        notification.setOuterid(outerId);//通知的
        notification.setNotifier(comment.getCommentator());//评论的创建者
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());//设置评论没有读过
        notification.setReceiver(receiver);//父评论人
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }


    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(commentators);


        // 获取评论人并转换为 Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));


        // 转换 comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
