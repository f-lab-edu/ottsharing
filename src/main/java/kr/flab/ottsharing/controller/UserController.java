package kr.flab.ottsharing.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.flab.ottsharing.protocol.MyInfo;
import kr.flab.ottsharing.protocol.MyPageUpdateResult;
import kr.flab.ottsharing.protocol.RegisterResult;
import kr.flab.ottsharing.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/register")
    public RegisterResult register(@RequestBody Map<String, String> req) {
        String userId = req.get("userId");
        String userPassword = req.get("userPassword");
        String email = req.get("email");

        return userService.register(userId, userPassword, email);
    }

    @GetMapping("/myPage")
    public MyInfo getMyInfo() {
        String userId = "user"; // 나중에 JWT login 구현되면 그에 맞게 자동으로 가져오도록 수정해야 함
        return userService.fetchMyInfo(userId);
    }


    @PutMapping("/myPage")
    public MyPageUpdateResult changeMyInfo(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String password = request.get("password");
        String email = request.get("email");

        return userService.updateMyInfo(userId, password, email);
    }

}
