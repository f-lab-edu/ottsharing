package kr.flab.ottsharing.controller;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.service.LoginService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public String IdCheck(@RequestParam String userId,HttpServletResponse response){

        User loginMember = loginService.loginCheck(userId);
        if(loginMember == null){
            loginMember = loginService.enrollUser(userId);
        }

        Cookie cookie = new Cookie("memberId",String.valueOf(loginMember.getUserId()));
        response.addCookie(cookie);


        return loginMember.getUserId();
    }

}
