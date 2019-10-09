package cheng.community.controller;

import cheng.community.dto.QuestionDTO;
import cheng.community.mapper.QuestionMapper;
import cheng.community.model.Question;
import cheng.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author cheng
 * @date 2019/10/9 0009 下午 15:18
 */
public class QuestionCotroller {
    @Autowired
    private QuestionService QuestionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id")Integer id
                            , Model model){
        QuestionDTO questionDTO =QuestionService.getById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
