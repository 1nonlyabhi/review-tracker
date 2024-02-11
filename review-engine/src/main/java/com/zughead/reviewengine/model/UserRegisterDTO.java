package com.zughead.reviewengine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class UserRegisterDTO {

  private String email;
  private String firstname;
  private String lastname;
  private String password;
  private String role;
}
