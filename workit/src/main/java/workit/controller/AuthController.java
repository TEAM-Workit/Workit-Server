package workit.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import workit.dto.user.LoginRequestDto;
import workit.service.AuthService;
import workit.util.ResponseCode;
import workit.util.ResponseMessage;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    /**
     * 카카오 callback
     * [GET] /auth/kakao/callback
     */

    // TODO: kakao token 받기 위한 함수
    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoGetAccessToken(@RequestParam String code) {
        System.out.println(code);
        String accessToken = authService.getKakaoAccessToken(code);
        System.out.println(accessToken);
    }

    @PostMapping("/login/{social}")
    public ResponseEntity<ResponseMessage> socialLogin(@PathVariable String social, @Valid @RequestBody LoginRequestDto requestDto) {
        return ResponseMessage.toResponseEntity(
                ResponseCode.LOGIN_SUCCESS,
                authService.socialLogin(social, requestDto.getSocialToken(), requestDto.getNickName())
        );
    }
}
