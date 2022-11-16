package workit.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import workit.entity.SocialType;

@Data
@AllArgsConstructor
public class SignupRequestDto {
    private String email;
    private String nickname;
    private SocialType socialType;
}
