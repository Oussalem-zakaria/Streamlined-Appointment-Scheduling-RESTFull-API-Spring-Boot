package com.ucd.CenterRDV.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public record CreneauDto(
        @DateTimeFormat(style = "hh:mm")
        LocalTime heureDebut,
        LocalTime heureFin,
        Integer centerId,
        Integer jourTravailId
) {
        // Définir les constantes pour l'heure de début minimale et maximale
        public static final LocalTime HEURE_DEBUT_MIN = LocalTime.of(8, 0); // Par exemple, 08:00 AM
        public static final LocalTime HEURE_DEBUT_MAX = LocalTime.of(18, 0); // Par exemple, 06:00 PM

        // Constructeur personnalisé pour vérifier les contraintes de l'heure de début
        public CreneauDto {
                // Vérifier si l'heure de début est dans la plage autorisée
                if (heureDebut.isBefore(HEURE_DEBUT_MIN) || heureDebut.isAfter(HEURE_DEBUT_MAX)) {
                        throw new IllegalArgumentException("Heure de début invalide. L'heure de début doit être entre " +
                                HEURE_DEBUT_MIN + " et " + HEURE_DEBUT_MAX);
                }
        }
}

