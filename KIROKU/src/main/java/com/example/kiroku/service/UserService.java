package com.example.kiroku.service;

import com.example.kiroku.entity.UserEntity;
import com.example.kiroku.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//UserService는 회원가입과 로그인 등 사용자 관련 비즈니스 로직을 처리하는 클래스이며,
// Repository를 통해 사용자 정보를 조회하거나 저장하는 역할
public class UserService {

    private final UserRepository userRepository;

    public void signup(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
    }

    public UserEntity login(String username, String password) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        return user;
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }
}
