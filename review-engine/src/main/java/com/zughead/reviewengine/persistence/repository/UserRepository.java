package com.zughead.reviewengine.persistence.repository;

import com.zughead.reviewengine.persistence.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(final String email);
}
