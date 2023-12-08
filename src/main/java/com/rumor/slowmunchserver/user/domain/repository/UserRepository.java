package com.rumor.slowmunchserver.user.domain.repository;

import com.rumor.slowmunchserver.user.domain.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findBySubject(String subject);

    void save(User user);
}

