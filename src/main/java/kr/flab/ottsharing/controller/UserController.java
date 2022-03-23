package kr.flab.ottsharing.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.MyInfo;
import kr.flab.ottsharing.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public String IdCheck(@RequestParam String userId,HttpServletResponse response){

        User loginMember = userService.loginCheck(userId);
        if(loginMember == null){
            loginMember = userService.enrollUser(userId);
        }

        Cookie cookie = new Cookie("memberId",String.valueOf(loginMember.getUserId()));
        response.addCookie(cookie);


        return loginMember.getUserId();
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("memberId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/myPage")
    public MyInfo getMyInfo() {
        String userId = "user"; // 나중에 JWT login 구현되면 그에 맞게 자동으로 가져오도록 수정해야 함
        return userService.fetchMyInfo(userId);
    }

}
