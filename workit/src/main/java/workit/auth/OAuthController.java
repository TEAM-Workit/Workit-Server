package workit.auth;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    @Autowired
    private OAuthService oAuthService;
    /**
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */

    // TODO: kakao token 받기 위한 함수
    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoGetAccessToken(@RequestParam String code) {
        System.out.println(code);
        String accessToken = oAuthService.getKakaoAccessToken(code);
        System.out.println(accessToken);
    }

    // TODO: 카카오 유저 정보를 받기 위한 함수
    @PostMapping("/user/kakao")
    public void kakaoCallback(@RequestParam String accessToken) {
        oAuthService.createKakaoUser(accessToken);
    }
}
