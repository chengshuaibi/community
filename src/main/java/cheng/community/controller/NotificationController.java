package cheng.community.controller;

import cheng.community.dto.NotificationDTO;
import cheng.community.dto.PaginationDTO;
import cheng.community.enums.NotificationTypeEnum;
import cheng.community.model.Notification;
import cheng.community.model.User;
import cheng.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cheng
 * @date 2019/10/23 0023 上午 9:32
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long    id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO =notificationService.read(id,user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType()==notificationDTO.getType()||
            NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDTO.getType()
        ){
            return "redirect:/question/"+notificationDTO.getOuterid();
        }else{
            return "redirect:/";
        }

    }
}
