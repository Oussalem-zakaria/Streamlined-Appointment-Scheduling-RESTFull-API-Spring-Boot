package com.ucd.CenterRDV.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Centre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String adresse;
    private String phoneNumber;

    @OneToMany(
            mappedBy = "centreSante",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private Set<Creneau> creneaux;

    @OneToMany(
            mappedBy = "centre",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private Set<RDV> rdvs;

    @OneToMany(mappedBy = "centre", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<JourTravail> joursTravail;

//    @OneToMany(mappedBy = "centreAdmin")
//    private Set<ApplicationUser> administrateurs;

}