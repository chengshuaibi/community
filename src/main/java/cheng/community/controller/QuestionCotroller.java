package cheng.community.controller;

import cheng.community.dto.CommentCreateDTO;
import cheng.community.dto.CommentDTO;
import cheng.community.dto.QuestionDTO;
import cheng.community.enums.CommentTypeEnum;
import cheng.community.model.Question;
import cheng.community.service.CommentService;
import cheng.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 *
 * @author cheng
 * @date 2019/10/9 0009 下午 15:18
 */
@Controller
public class QuestionCotroller {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService CommentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id
                            , Model model){
        QuestionDTO questionDTO =questionService.getById(id);
        List<QuestionDTO> questionDTOS = questionService.selectRelated(questionDTO);

        List<CommentDTO> comments= CommentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("questionDTOS",questionDTOS);
        return "question";
    }
}
