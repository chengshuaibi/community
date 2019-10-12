package cheng.community.controller;

import cheng.community.Exception.CustomizeErrorCode;
import cheng.community.Exception.ICustomizeErrorCode;
import cheng.community.dto.CommentDTO;
import cheng.community.dto.ResultDTO;
import cheng.community.mapper.CommentMapper;
import cheng.community.model.Comment;
import cheng.community.model.User;
import cheng.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cheng
 * @date 2019/10/11 0011 下午 15:04
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;

        @ResponseBody
        @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
            User user = (User) request.getSession().getAttribute("user");
            if (user==null){
                return  ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
            }
            Comment comment=new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(1l);
        comment.setLikeCount(1l);
        Map<Object,Object> objectObjectMap=new HashMap<>();
        objectObjectMap.put("message","成功");
        return objectObjectMap;



    }
}
