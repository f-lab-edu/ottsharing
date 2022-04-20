package kr.flab.ottsharing.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.MyInfo;
import kr.flab.ottsharing.protocol.MyPageUpdateResult;
import kr.flab.ottsharing.protocol.RegisterResult;
import kr.flab.ottsharing.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request, HttpServletResponse response){
        String userId = request.get("userId");
        User user = userService.login(userId);
        if (user == null) {
            return "로그인 실패";
        }

        Cookie cookie = new Cookie("userId", userId);
        response.addCookie(cookie);

        return "로그인 성공";
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("userId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/register")
    public RegisterResult register(@RequestBody Map<String, String> req) {
        String userId = req.get("userId");
        String userPassword = req.get("userPassword");
        String email = req.get("email");

        return userService.register(userId, userPassword, email);
    }

    @GetMapping("/myPage")
    public MyInfo getMyInfo(@CookieValue(name = "userId") String userId) {
        return userService.fetchMyInfo(userId);
    }

    @PutMapping("/myPage")
    public MyPageUpdateResult changeMyInfo(@CookieValue(name = "userId") String userId, @RequestBody Map<String, String> request) {
        String password = request.get("password");
        String email = request.get("email");

        return userService.updateMyInfo(userId, password, email);
    }
}
