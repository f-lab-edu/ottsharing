package kr.flab.ottsharing.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import kr.flab.ottsharing.protocol.MyPageUpdateResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @PutMapping("/myPage")
    public MyPageUpdateResult changeMyInfo(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String password = request.get("password");
        String email = request.get("email");

        return userService.updateMyInfo(userId, password, email);

    }
}
