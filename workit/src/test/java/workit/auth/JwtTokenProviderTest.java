package workit.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.jdbc.Sql;
import workit.util.CustomException;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql({"/schema.sql", "/data.sql"})
class JwtTokenProviderTest {

    private static final String SECRET_KEY = "jeonghoonhyunji1234567890abcdefg9765hijklmn1234";

    @Autowired
    private UserDetailsService userDetailsService;
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(SECRET_KEY, userDetailsService);
    }

    @Nested
    @DisplayName("토큰 생성 테스트")
    class CreateToken {

        @Test
        @DisplayName("성공")
        void success() {
            // given
            String email = "only_read@example.com";

            // when
            String token = jwtTokenProvider.createToken(email);

            // then
            assertThat(jwtTokenProvider.getUserEmail(token)).isEqualTo(email);
        }
    }

    @Nested
    @DisplayName("인가 테스트")
    class AuthenticationTest {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            String email = "only_read@example.com";
            String token = jwtTokenProvider.createToken(email);

            // when
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            // then
            assertThat(authentication.getName()).isEqualTo("1");
        }

        @Test
        @DisplayName("실패 - 잘못된 이메일")
        void fail_invalid_email() {
            //given
            String email = "invalid@example.com";
            String token = jwtTokenProvider.createToken(email);

            // then
            assertThatThrownBy(() -> jwtTokenProvider.getAuthentication(token))
                    .isInstanceOf(CustomException.class);
        }
    }

    @Nested
    @DisplayName("유저 이메일 검색")
    class GetUserEmail {

        @Test
        @DisplayName("성공")
        void success() {
            // given
            String email = "invalid@example.com";
            String token = jwtTokenProvider.createToken(email);

            // when
            String searchedEmail = jwtTokenProvider.getUserEmail(token);

            // then
            assertThat(searchedEmail).isEqualTo(email);
        }
    }

    @Nested
    @DisplayName("토큰 검증 테스트")
    class ValidateToken {

        @Test
        @DisplayName("성공")
        void success() {
            // given
            String email = "invalid@example.com";
            String token = jwtTokenProvider.createToken(email);

            // when
            boolean validated = jwtTokenProvider.validateToken(token);

            // then
            assertThat(validated).isTrue();
        }

        @Test
        @DisplayName("실패 - 만료된 토큰")
        void fail_expired_token() {
            // given
            String email = "invalid@example.com";
            String token = createExpiredToken(email);

            // when

        }

        private String createExpiredToken(String email) {
            Claims claims = Jwts.claims().setSubject(email);
            Date now = new Date();

            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime()))
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();
        }
    }
}
