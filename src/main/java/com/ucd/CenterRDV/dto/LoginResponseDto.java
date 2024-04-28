package com.ucd.CenterRDV.dto;

import com.ucd.CenterRDV.models.ApplicationUser;
import lombok.Data;

@Data
public class LoginResponseDto {
    private ApplicationUser user;
    private String jwt;

    public LoginResponseDto(){
        super();
    }

    public LoginResponseDto(ApplicationUser user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }

}
