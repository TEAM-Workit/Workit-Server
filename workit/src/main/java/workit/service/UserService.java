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

    public UserInfoResponseDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        return new UserInfoResponseDto(user.getEmail(), user.getNickname());
    }

    public UserNicknameResponseDto getUserNickname(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        return new UserNicknameResponseDto(user.getNickname());
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        userRepository.delete(user);
    }
}
