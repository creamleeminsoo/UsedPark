package me.leeminsoo.usedpark.controller.mvc.user;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.UserNotAuthenticationException;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.user.AddUserRequestDTO;
import me.leeminsoo.usedpark.dto.user.UpdateUserRequestDTO;
import me.leeminsoo.usedpark.dto.user.UserResponse;
import me.leeminsoo.usedpark.service.user.UserService;
import me.leeminsoo.usedpark.util.CookieUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequestDTO request) {
        userService.save(request);
        return "login";

    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        deleteCookie(request,response);
    }

    @PutMapping("/mypage")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequestDTO dto,
                                             @AuthenticationPrincipal User user,HttpServletRequest request,HttpServletResponse response){
        userService.update(dto,user.getId());
        deleteCookie(request,response);
        return ResponseEntity.ok().body("유저 업데이트 성공");

    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String sighup(@AuthenticationPrincipal User user, Model model) {
        if (user == null) {
            UserResponse userResponse = new UserResponse();
            model.addAttribute("user",userResponse);
            return "signup";
        } else {
            model.addAttribute("user", user);
            return "signup";
        }
    }

    public void deleteCookie(HttpServletRequest request,HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request,response, SecurityContextHolder.getContext().getAuthentication());
        CookieUtil.deleteCookie(request, response, "access_token");
        CookieUtil.deleteCookie(request, response, "refresh_token");
        response.setStatus(HttpServletResponse.SC_OK);

    }
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "userId") Long userId, @AuthenticationPrincipal User user){
        if (user == null){
            throw new UserNotAuthenticationException();
        }
        if (userId.equals(user.getId())){
            userService.deleteUser(userId,user);
            return ResponseEntity.ok().build();
        }else throw new AccessDeniedException("권한이 없습니다");
    }
}
