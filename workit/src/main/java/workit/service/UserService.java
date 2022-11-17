package workit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workit.dto.user.UserInfoResponseDto;
import workit.dto.user.UserNicknameResponseDto;
import workit.entity.User;
import workit.repository.UserRepository;
import workit.util.CustomException;
import workit.util.ResponseCode;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserInfoResponseDto getUserInfo(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        return new UserInfoResponseDto(user.getEmail(), user.getNickname());
    }

    public UserNicknameResponseDto getUserNickname(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        return new UserNicknameResponseDto(user.getNickname());
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        user.setDeleted(true);
        userRepository.save(user);
    }
}
