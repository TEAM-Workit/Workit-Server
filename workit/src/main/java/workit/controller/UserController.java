package workit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseMessage> getUserInfo(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();

        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_USER_INFO_SUCCESS,
                userService.getUserInfo(email)
        );
    }

    @GetMapping("/nickname")
    public ResponseEntity<ResponseMessage> getUserNickname(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();

        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_USER_INFO_SUCCESS,
                userService.getUserNickname(email)
        );
    }

    @DeleteMapping
    public ResponseEntity<ResponseNonDataMessage> deleteUser(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        userService.deleteUser(email);

        return ResponseNonDataMessage.toResponseEntity(
                ResponseCode.DELETE_USER_SUCCESS
        );
    }
}
