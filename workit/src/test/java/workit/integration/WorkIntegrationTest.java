package workit.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class WorkIntegrationTest extends IntegrationTest {

    @Nested
    @DisplayName("전체 워킷 조회 API - POST /work")
    class GetAll {

        @Test
        @DisplayName("성공")
        void success() {
//            // given
//            Long userId = 1L;
//
//            // when
//            given()
//                    .auth().o.basic(member.getEmail(), "invalid password")
//                    .when().post("/orders")
//                    .then()
//                    .extract();

        }
    }
}
