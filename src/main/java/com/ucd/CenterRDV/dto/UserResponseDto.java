package com.ucd.CenterRDV.dto;

import com.ucd.CenterRDV.models.RDV;

import java.util.Set;

public record UserResponseDto(
        String userName,
        String firstName,
        String lastName,
        String phoneNumber,
        String role,
        Set<RDV> rdvs
) {
}
