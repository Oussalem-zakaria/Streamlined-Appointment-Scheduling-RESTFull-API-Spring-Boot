package com.ucd.CenterRDV.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank(message = "vouilez vous saisir votre email")
    @Email(message = "saisir un email valid (ex: xyz@xyz.com)")
    private String username;
    @NotBlank(message = "vouilez vous saisir votre mot de passe")
    private String password;
}
