package workit.auth;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class OAuthController {
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

    @PostMapping("/login/{social}")
    public void socialLogin(@PathVariable String social, @RequestParam String socialToken) {
        return
        oAuthService.createKakaoUser(socialToken);
    }
}
