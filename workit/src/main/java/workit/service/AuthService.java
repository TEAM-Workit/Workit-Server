package workit.service;

import com.google.gson.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import workit.auth.JwtTokenProvider;
import workit.dto.user.LoginResponseDto;
import workit.dto.user.SignupRequestDto;
import workit.entity.SocialType;
import workit.entity.User;
import workit.repository.UserRepository;
import workit.util.CustomException;
import workit.util.ResponseCode;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Objects;

import static org.springframework.security.config.Elements.JWT;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final String KAKAO = "KAKAO";
    private static final String APPLE = "APPLE";

    @Value("${kakao.authUrl}")
    private String kakaoAuthURL;

    @Value("${kakao.user-api-url}")
    private String kakaoUserApiUrl;

    @Value("${kakao.restapi-key}")
    private String restapiKey;

    @Value("${kakao.redirect-url}")
    private String redirectUrl;

    @Value("${apple.apple-url}")
    private String appleUrl;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponseDto socialLogin(String social, String socialToken, String userName) {
        if (social.equals(KAKAO)) {
            return kakaoLogin(socialToken);
        } else if (social.equals(APPLE)) {
            return appleLogin(socialToken, userName);
        }
        throw new CustomException(ResponseCode.INVALID_SOCIAL_TYPE);
    }

    // TODO: 나중에 버릴 함수
    public String getKakaoAccessToken (String code) {
        String access_Token = "";
        String refresh_Token = "";

        try {
            URL url = new URL(kakaoAuthURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=").append(restapiKey);
            sb.append("&redirect_uri=").append(redirectUrl);
            sb.append("&code=").append(code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    private LoginResponseDto kakaoLogin(String socialToken) {
        try {
            URL url = new URL(kakaoUserApiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + socialToken);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();

            JsonElement element = JsonParser.parseString(result.toString());

            boolean disagreeEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email_needs_agreement").getAsBoolean();
            String email = "";
            if(!disagreeEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }
            String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

            String accessToken = getAccessToken(new SignupRequestDto(email, nickname, SocialType.KAKAO));

            return new LoginResponseDto(accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new CustomException(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    private LoginResponseDto appleLogin(String socialToken, String nickName) {
        StringBuilder result = new StringBuilder();
        try {
            if (nickName == null) {
                throw new CustomException(ResponseCode.NO_VALUE_REQUIRED);
            }

            URL url = new URL(appleUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();

            JsonObject keys = (JsonObject) JsonParser.parseString(result.toString());
            JsonArray keyArray = (JsonArray) keys.get("keys");
            String[] decodeArray = socialToken.split("\\.");
            String header = new String(Base64.getDecoder().decode(decodeArray[0]));

            JsonElement kid = ((JsonObject) JsonParser.parseString(header)).get("kid");
            JsonElement alg = ((JsonObject) JsonParser.parseString(header)).get("alg");

            JsonObject avaliableObject = null;
            for (int i = 0; i < keyArray.size(); i++) {
                JsonObject appleObject = (JsonObject) keyArray.get(i);
                JsonElement appleKid = appleObject.get("kid");
                JsonElement appleAlg = appleObject.get("alg");

                if (Objects.equals(appleKid, kid) && Objects.equals(appleAlg, alg)) {
                    avaliableObject = appleObject;
                    break;
                }
            }

            if (ObjectUtils.isEmpty(avaliableObject)) {
                throw new CustomException(ResponseCode.BAD_REQUEST);
            }

            PublicKey publicKey = this.getPublicKey(avaliableObject);


            Claims userInfo = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(socialToken).getBody();
            JsonObject userInfoObject = (JsonObject) JsonParser.parseString(new Gson().toJson(userInfo));
            String email = userInfoObject.get("email").getAsString();
            System.out.println(email);

            String accessToken = getAccessToken(new SignupRequestDto(email, nickName, SocialType.APPLE));

            return new LoginResponseDto(accessToken);
        } catch (IOException e) {
            throw new CustomException(ResponseCode.FAILED_VALIDATE_APPLE_LOGIN);
        }
    }

    private PublicKey getPublicKey(JsonObject object) {
        String nStr = object.get("n").toString();
        String eStr = object.get("e").toString();

        byte[] nBytes = Base64.getUrlDecoder().decode(nStr.substring(1, nStr.length() - 1));
        byte[] eBytes = Base64.getUrlDecoder().decode(eStr.substring(1, eStr.length() - 1));

        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);

        try {
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception exception) {
            throw new CustomException(ResponseCode.BAD_REQUEST);
        }
    }

    private String getAccessToken(SignupRequestDto requestDto) {
        if (!userRepository.existsByEmail(requestDto.getEmail())) {
            User user = userRepository.save(new User(requestDto));
            return jwtTokenProvider.createToken(user.getEmail());
        }
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new CustomException(ResponseCode.LOGIN_FAILED)
        );
        return jwtTokenProvider.createToken(user.getEmail());
    }
}
