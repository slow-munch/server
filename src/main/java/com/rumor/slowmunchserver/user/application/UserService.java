package com.rumor.slowmunchserver.user.application;

import com.rumor.slowmunchserver.user.domain.User;
import com.rumor.slowmunchserver.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(User user) {
        Optional<User> userEntity = userRepository.findBySubject(user.getSubject());

        if (!userEntity.isPresent()) {
            userRepository.save(user);
        }
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found User"));
    }
}
