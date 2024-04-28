package com.ucd.CenterRDV.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RdvDto(
        @NotNull(message = "L'identifiant de l'utilisateur ne peut pas être null")
        LocalDate date,
        @NotNull(message = "L'identifiant de l'utilisateur ne peut pas être null")
        Integer userId,
        @NotNull(message = "Veuillez sélectionner un centre")
        Integer centerId,
        @NotNull(message = "Veuillez sélectionner un créneau")
        Integer creneauId,
        @NotNull(message = "Veuillez sélectionner un jour")
        String jour,
        Boolean statut
) {
}
