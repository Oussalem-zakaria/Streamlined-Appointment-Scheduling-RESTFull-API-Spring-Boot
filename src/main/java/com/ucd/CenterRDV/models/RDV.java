package com.ucd.CenterRDV.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RDV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private ApplicationUser user;

    @ManyToOne
    @JoinColumn(name = "centre_id")
    @JsonBackReference
    private Centre centre;

    @ManyToOne
    @JoinColumn(name = "creneau_id")
    @JsonBackReference
    private Creneau creneau;

    private Boolean statut = false;
}