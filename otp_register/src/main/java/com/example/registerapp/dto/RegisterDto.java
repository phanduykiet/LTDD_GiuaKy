package com.example.registerapp.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

  private String name;
  private String email;
  private String password;
  private String avatar;
}
