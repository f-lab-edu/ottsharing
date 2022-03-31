package kr.flab.ottsharing.controller;

import kr.flab.ottsharing.protocol.MyPageUpdateResult;
import org.springframework.web.bind.annotation.*;
import kr.flab.ottsharing.protocol.MyInfo;
import kr.flab.ottsharing.protocol.UserDeleteResult;
import kr.flab.ottsharing.service.UserService;
import lombok.RequiredArgsConstructor;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    /* 추후 변경해야 할 코드 - 현재 구버전이므로 주석처리
    @PostMapping("/login")
    public String IdCheck(@RequestParam String userId,HttpServletResponse response){

        User loginMember = userService.loginCheck(userId);
        if(loginMember == null){
            loginMember = userService.enrollUser(userId);
        }
    }*/ 

    @PutMapping("/myPage")
    public MyPageUpdateResult changeMyInfo(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String password = request.get("password");
        String email = request.get("email");

        return userService.updateMyInfo(userId, password, email);
    }
    
    /* 추후 변경해야 할 코드 - 현재 구버전이므로 주석처리
    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("memberId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
    */

    @DeleteMapping("/myPage")
    public UserDeleteResult leave() {
        String userId = "user"; // 추후 JWT login 구현되면 현재 로그인 된 아이디 가져오도록 수정
        return userService.deleteMyInfo(userId);
    }
      
    @GetMapping("/myPage")
    public MyInfo getMyInfo() {
        String userId = "user"; // 나중에 JWT login 구현되면 그에 맞게 자동으로 가져오도록 수정해야 함
        return userService.fetchMyInfo(userId);
    }

}
