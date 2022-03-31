package kr.flab.ottsharing.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.flab.ottsharing.protocol.MyInfo;
import kr.flab.ottsharing.protocol.MyPageUpdateResult;
import kr.flab.ottsharing.protocol.RegisterResult;
import kr.flab.ottsharing.protocol.UserDeleteResult;
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

    @DeleteMapping("/myPage")
    public UserDeleteResult leave() {
        String userId = "user"; // 추후 JWT login 구현되면 현재 로그인 된 아이디 가져오도록 수정
        return userService.deleteMyInfo(userId);
    }

    // LoginService -> UserService로 이름 변경 및 구조개선으로 인해 동작하지 않아 주석처리
/*
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

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("memberId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
*/
}
