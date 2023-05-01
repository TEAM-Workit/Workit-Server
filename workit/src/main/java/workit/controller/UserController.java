package workit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import workit.dto.user.UserModifyDto;
import workit.service.UserService;
import workit.util.ResponseCode;
import workit.util.ResponseMessage;
import workit.util.ResponseNonDataMessage;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @PatchMapping
    public ResponseMessage modifyUser(@Valid @RequestBody UserModifyDto userModifyDto, HttpServletRequest request) {
        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.MODIFY_USER_SUCCESS,
                userService.modifyUser(userId, userModifyDto)
        );
    }
}
