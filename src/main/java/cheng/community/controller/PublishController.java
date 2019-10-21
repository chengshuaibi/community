package cheng.community.controller;

import cheng.community.cache.TagCache;
import cheng.community.dto.QuestionDTO;
import cheng.community.model.Question;
import cheng.community.model.User;
import cheng.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 发布问题
 * @author cheng
 * @date 2019/10/5 0005 上午 11:52
 */

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());

        model.addAttribute("tags", TagCache.get());
        return "publish";
    }


    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }


    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Long id,
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
        model.addAttribute("tags", TagCache.get());

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
        String filterinvalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(filterinvalid)){
            model.addAttribute("error","输入非法标签"+filterinvalid);
            return "publish";
        }
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getId());
        question.setId(id);
        //判断问题是否在数据库中存在，有就创建，没有就更新
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
