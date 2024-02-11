package com.zughead.reviewengine.rest;

import com.zughead.reviewengine.model.UserAuthDTO;
import com.zughead.reviewengine.model.UserLoginDTO;
import com.zughead.reviewengine.model.UserRegisterDTO;
import com.zughead.reviewengine.model.UserResponseDTO;
import com.zughead.reviewengine.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  @Autowired AuthenticationService authService;

  @PostMapping("/register")
  public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO request) {
    return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
  }

  @PostMapping("/authenticate")
  public ResponseEntity<UserAuthDTO> authenticate(@RequestBody UserLoginDTO request) {
    return new ResponseEntity<>(authService.authenticate(request), HttpStatus.ACCEPTED);
  }
}
