package uniVerse.posterPlot.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniVerse.posterPlot.dto.SignUpRequestDto;
import uniVerse.posterPlot.entity.UserEntity;
import uniVerse.posterPlot.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    // 회원가입
    @Transactional
    public void signUp(SignUpRequestDto requestDto) {

        String id = requestDto.getId();
        String password = requestDto.getPassword();
        String confirmPassword = requestDto.getConfirmPassword();
        String email = requestDto.getEmail();

        if (userRepository.existById(id)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        if (userRepository.existByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (id == null || password == null || confirmPassword == null ||email == null) {
            throw new IllegalArgumentException("값을 입력해주세요.");
        }

        if (!email.matches("\\\\w+@\\\\w+\\\\.\\\\w+(\\\\.\\\\w+)?")){
            throw new IllegalArgumentException("유효한 이메일 형식이 아닙니다.");
        }

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.");
        }

        //이메일 인증 메서드

        UserEntity user = new UserEntity(id, password, email);
        userRepository.save(user);
    }


    // 로그인



}
