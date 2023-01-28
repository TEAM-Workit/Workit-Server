package workit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workit.service.UserService;
import workit.util.ResponseCode;
import workit.util.ResponseMessage;
import workit.util.ResponseNonDataMessage;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseMessage getUserInfo(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_USER_INFO_SUCCESS,
                userService.getUserInfo(userId)
        );
    }

    @GetMapping("/nickname")
    public ResponseMessage getUserNickname(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_USER_INFO_SUCCESS,
                userService.getUserNickname(userId)
        );
    }

    @DeleteMapping
    public ResponseNonDataMessage deleteUser(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getUserPrincipal().getName());
        userService.deleteUser(userId);

        return ResponseNonDataMessage.toResponseEntity(
                ResponseCode.DELETE_USER_SUCCESS
        );
    }
}
