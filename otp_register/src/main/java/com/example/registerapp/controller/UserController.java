package com.example.registerapp.controller;

import com.example.registerapp.dto.LoginDto;
import com.example.registerapp.dto.RegisterDto;
import com.example.registerapp.entity.User;
import com.example.registerapp.service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDto registerDto) {
      return userService.register(registerDto);
  }


  @PostMapping("/verify")
  public ResponseEntity<Map<String, String>> verifyAccount(@RequestParam String email,
                                                           @RequestParam String otp) {
      return (ResponseEntity<Map<String, String>>) userService.verifyAccount(email, otp);
  }

  @PostMapping("/regenerate-otp")
  public ResponseEntity<Map<String, String>> regenerateOtp(@RequestParam String email) {
      return (ResponseEntity<Map<String, String>>) userService.regenerateOtp(email);
  }

  @PostMapping("/login")
  public User login(@RequestBody LoginDto loginDto) {
    return userService.login(loginDto);
  }
  @PostMapping("/reset-password")
  public ResponseEntity<String> resetPassword(@RequestParam String newPassword,@RequestParam String email) {
    return new ResponseEntity<>(userService.resetPassword(newPassword,email), HttpStatus.OK);
  }
}
