package com.ucd.CenterRDV.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationDto {
    @NotBlank(message = "vouilez vous saisir votre email")
    @Email(message = "saisir un email valid (ex: xyz@xyz.com)")
    private String username;
    @NotBlank(message = "vouilez vous saisir votre mot de passe")
    private String password;
    @NotBlank(message = "vouilez vous saisir votre prénom")
    private String firstName;
    @NotBlank(message = "vouilez vous saisir votre nom")
    private String lastName;
    @NotBlank(message = "vouilez vous saisir votre numero de telephone")
    private String phoneNumber;

    public RegistrationDto(){
        super();
    }

    public RegistrationDto(String username, String password){
        super();
        this.username = username;
        this.password = password;
    }

    public String toString(){
        return "Registration info: username: " + this.username + " password: " + this.password;
    }
}
