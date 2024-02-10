package com.zughead.reviewengine.security.config;

import com.zughead.reviewengine.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class AppConfig {

  @Autowired private UserRepository userRepository;

  @Bean
  public UserDetailsService userDetailsService() {
    return userEmail ->
        userRepository
            .findByEmail(userEmail)
            .orElseThrow(() -> new UsernameNotFoundException("User Not found"));
  }
}
