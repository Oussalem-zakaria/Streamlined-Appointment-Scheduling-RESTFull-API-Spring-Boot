package com.ucd.CenterRDV.controllers;

import com.ucd.CenterRDV.models.ApplicationUser;
import com.ucd.CenterRDV.dto.LoginDto;
import com.ucd.CenterRDV.dto.LoginResponseDto;
import com.ucd.CenterRDV.dto.RegistrationDto;
import com.ucd.CenterRDV.services.AuthenticationService;
import com.ucd.CenterRDV.services.PasswordResetService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class  AuthenticationController {

    private AuthenticationService authenticationService;
    private PasswordResetService passwordResetService;

    public AuthenticationController(AuthenticationService authenticationService, PasswordResetService passwordResetService) {
        this.authenticationService = authenticationService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody @Valid RegistrationDto body){
        return authenticationService.registerUser(body.getUsername(), body.getPassword(),body.getFirstName(),body.getLastName(),body.getPhoneNumber());
    }

    @PostMapping("/login")
    public LoginResponseDto loginUser(@RequestBody @Valid LoginDto body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }

    @PostMapping("/forgot-password")
    public void sendPasswordResetRequest(@RequestParam String email) throws MessagingException {
        passwordResetService.sendPasswordResetRequest(email);
    }

    @GetMapping("/reset-password")
    public ModelAndView showResetPasswordForm(@RequestParam(required = false) String token,@RequestParam String email, Model model) throws MessagingException {
        if (token != null) {
            // Validate the token (call the new method)
            boolean validToken = passwordResetService.isValidToken(token);
            model.addAttribute("validToken", validToken);
            if (validToken) {
                // Pre-populate the token field if valid
                model.addAttribute("token", token);
                model.addAttribute("email", email);
            }
        }
        return new ModelAndView("reset-password"); // Name of your Thymeleaf template
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token,@RequestParam String email, @RequestParam String newPassword) {
        try {
            passwordResetService.resetPassword(token, newPassword,email);
            return ResponseEntity.ok("Password reset successful!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
