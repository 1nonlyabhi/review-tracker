package com.zughead.reviewengine.service;

import com.zughead.reviewengine.mapper.UserResponseDTOMapper;
import com.zughead.reviewengine.model.UserAuthDTO;
import com.zughead.reviewengine.model.UserLoginDTO;
import com.zughead.reviewengine.model.UserRegisterDTO;
import com.zughead.reviewengine.model.UserResponseDTO;
import com.zughead.reviewengine.persistence.entity.User;
import com.zughead.reviewengine.persistence.repository.UserRepository;
import com.zughead.reviewengine.security.service.JwtService;
import com.zughead.reviewengine.security.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  @Autowired private AuthenticationManager authenticationManager;
  @Autowired private JwtService jwtService;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private UserRepository userRepository;
  @Autowired private UserResponseDTOMapper userResponseDTOMapper;

  public UserResponseDTO register(final UserRegisterDTO userRegisterDTO) {
    var user =
        User.builder()
            .email(userRegisterDTO.getEmail())
            .firstname(userRegisterDTO.getFirstname())
            .lastname(userRegisterDTO.getLastname())
            .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
            .role(Role.USER)
            .build();
    return userResponseDTOMapper.getUserResponseDTO(userRepository.save(user));
  }

  public UserAuthDTO authenticate(final UserLoginDTO userLoginDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            userLoginDTO.getEmail(), userLoginDTO.getPassword()));
    User user = userRepository.findByEmail(userLoginDTO.getEmail()).orElseThrow();
    String accessToken = jwtService.generateToken(user);
    return UserAuthDTO.builder().accessToken(accessToken).build();
  }
}
