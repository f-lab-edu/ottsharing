package kr.flab.ottsharing.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.flab.ottsharing.dto.request.LoginDto;
import kr.flab.ottsharing.dto.request.RegisterDto;
import kr.flab.ottsharing.dto.request.UserUpdateDto;
import kr.flab.ottsharing.dto.response.MyInfo;
import kr.flab.ottsharing.dto.response.common.CommonResponse;
import kr.flab.ottsharing.dto.response.common.ResultCode;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public CommonResponse login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        String userId = loginDto.getUserId();
        User user = userService.login(userId);
        if (user == null) {
            return new CommonResponse(ResultCode.LOGIN_FAILED);
        }

        Cookie cookie = new Cookie("userId", userId);
        response.addCookie(cookie);

        return new CommonResponse();
    }

    @PostMapping("/logout")
    public CommonResponse logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("userId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return new CommonResponse();
    }

    @PostMapping("/register")
    public CommonResponse register(@RequestBody RegisterDto registerDto) {
        String userId = registerDto.getUserId();
        String userPassword = registerDto.getUserPassword();
        String email = registerDto.getEmail();

        return userService.register(userId, userPassword, email);
    }

    @GetMapping("/myPage")
    public MyInfo getMyInfo(@CookieValue(name = "userId") String userId) {
        return userService.fetchMyInfo(userId);
    }

    @PutMapping("/myPage")
    public CommonResponse changeMyInfo(@CookieValue(name = "userId") String userId, @RequestBody UserUpdateDto updateDto) {
        String password = updateDto.getPassword();
        String email = updateDto.getEmail();

        return userService.updateMyInfo(userId, password, email);
    }
}
