package com.cqw.community.controller;

import com.cqw.community.dao.UserDao;
import com.cqw.community.domain.AccessTokenRequest;
import com.cqw.community.domain.GithubUser;
import com.cqw.community.domain.database.User;
import com.cqw.community.util.AuthorizeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectURL;

    @Autowired
    private UserDao userDao;

    /**
     * 该请求由Github回调，github会传code和state,其中state的检验由我们服务器实现,github只负责传递
     */
    @RequestMapping("/loginCallback")
    public String loginCallback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, HttpServletRequest request) {
        AccessTokenRequest accessTokenRequest = new AccessTokenRequest();
        accessTokenRequest.setClient_id(clientId);
        accessTokenRequest.setClient_secret(clientSecret);
        accessTokenRequest.setCode(code);
        accessTokenRequest.setRedirect_uri(redirectURL);  //没有回调，github只返回Json
        accessTokenRequest.setState(state);
        String accessToken = AuthorizeUtil.getAccessToken(accessTokenRequest);
        GithubUser user = AuthorizeUtil.getGithubUser(accessToken);
        if (user != null) {
            User u = new User();
            u.setAccount_id(user.getId());
            u.setName(user.getLogin());
            u.setToken(UUID.randomUUID().toString());
            u.setGmt_create(System.currentTimeMillis());
            u.setGmt_modified(System.currentTimeMillis());
            userDao.insert(u);
            request.getSession().setAttribute("user", user);
        }
        return "redirect:/";
    }
}
