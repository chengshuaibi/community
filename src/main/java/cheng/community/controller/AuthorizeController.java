package cheng.community.controller;

import cheng.community.mapper.UserMapper;
import cheng.community.provider.GitHubProvider;
import cheng.community.dto.AccessTokenDTO;
import cheng.community.dto.GithubUser;
import cheng.community.model.User;
import cheng.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author cheng
 * @date 2019/9/28 0028 上午 11:21
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;
    @Value("${github.Client_id}")
    private String client_id;
    @Value("${github.Client_secret}")
    private String setclient_secret;
    @Value("${github.Redirect_uri}")
    private String redirect_uri;
    @Autowired
    private UserService userService;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(setclient_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = gitHubProvider.getuser(accessToken);
        if (githubUser!=null&&githubUser.getId()!=null){
            //封装user进数据库
            User user=new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));

            user.setAvatarUrl(githubUser.getAvatarUrl());
            //判断是否存在user，相同则不创建，不同则创建
            userService.createOrUpdate(user);
            //发送cookies携带token
            response.addCookie(new Cookie("token",token));
            //登录成功

            return "redirect:/";
        }else {
            //登录失败
        return "redirect:/";
        }

    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
            request.getSession().invalidate();
            Cookie cookie=new Cookie("token",null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return "redirect:/";

    }

}
