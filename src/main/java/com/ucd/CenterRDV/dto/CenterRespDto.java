package com.ucd.CenterRDV.dto;

import java.util.List;

public record CenterRespDto(
        String name,
        String adresse,
        String phoneNumber,
        List<String> joursTravail
) {
}

