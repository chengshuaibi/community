package cheng.community.controller;

import cheng.community.mapper.QuestionMapper;
import cheng.community.model.Question;
import cheng.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

/**
 * @author cheng
 * @date 2019/10/5 0005 上午 11:52
 */

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;


    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }


    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model) {
        /*
         *文章发布后内容显示
         * @param [title, description, tag, request, model]
         * @return java.lang.String
         */
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        /*
         *判断标题等是否为空
         * @param [title, description, tag, request, model]
         * @return java.lang.String
         */
        if (title == null || title=="") {
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null|| description=="") {
            model.addAttribute("error","问题内容不能空");
            return "publish";
        }  if (tag == null|| tag=="") {
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
                model.addAttribute("error","用户未登录");
            }
        question.setCreator(user.getId  ());


        questionMapper.create(question);
        return "redirect:/";
    }
}
