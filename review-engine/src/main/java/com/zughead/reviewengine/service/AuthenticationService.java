package com.zughead.reviewengine.service;

import com.zughead.reviewengine.mapper.UserResponseDTOMapper;
import com.zughead.reviewengine.model.UserAuthDTO;
import com.zughead.reviewengine.model.UserLoginDTO;
import com.zughead.reviewengine.model.UserRegisterDTO;
import com.zughead.reviewengine.model.UserResponseDTO;
import com.zughead.reviewengine.persistence.entity.Token;
import com.zughead.reviewengine.persistence.entity.User;
import com.zughead.reviewengine.persistence.repository.TokenRepository;
import com.zughead.reviewengine.persistence.repository.UserRepository;
import com.zughead.reviewengine.security.enums.Role;
import com.zughead.reviewengine.security.enums.TokenType;
import com.zughead.reviewengine.security.service.JwtService;
import java.util.List;
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
  @Autowired private TokenRepository tokenRepository;
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
    revokeAllUserTokens(user);
    saveUserToken(user, accessToken);
    return UserAuthDTO.builder().accessToken(accessToken).build();
  }

  private void saveUserToken(User user, String jwtToken) {
    Token token =
        Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    List<Token> validTokens = tokenRepository.findAllValidTokensByUser(user.getId());
    if (validTokens.isEmpty()) {
      return;
    }
    validTokens.forEach(
        token -> {
          token.setRevoked(true);
          token.setExpired(true);
        });
  }
}
